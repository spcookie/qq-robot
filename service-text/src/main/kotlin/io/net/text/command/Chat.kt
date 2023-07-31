package io.net.text.command

import com.alibaba.csp.sentinel.EntryType
import com.alibaba.csp.sentinel.annotation.SentinelResource
import com.alibaba.csp.sentinel.slots.block.BlockException
import com.cn.augenstern.manager.ArgsMin
import io.net.api.base.*
import io.net.api.enum.CmdEnum
import io.net.api.exception.GroupCmdException
import io.net.text.bo.ChatCompletionRequest
import io.net.text.bo.ChatCompletionResult
import io.net.text.bo.ChatMessage
import io.net.text.bo.ChatMessageRole
import io.net.text.config.AppProperty
import io.net.text.config.SentinelRule
import io.net.text.redis.RedisChatUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.validation.annotation.Validated
import org.springframework.web.client.RestTemplate


/**
 *@author Augenstern
 *@date 2023/6/1
 */
@Suppress("unused")
@Validated
@Cmd(cmd = CmdEnum.CHAT)
class Chat(val appProperty: AppProperty, @Qualifier("proxy") val restTemplate: RestTemplate) : AbstractCmd {

    @set:Autowired
    @setparam:Lazy
    lateinit var self: Chat

    companion object {
        private const val OPENAI_MAX_TOKENS = 4096
        private const val CHAT_URL = "https://api.openai.com/v1/chat/completions"
    }

    override fun describe() = """
        ##聊天##
          chat(text)
          text-聊天内容
    """.trimIndent()

    override fun args(): ArgsMergeStrategy = ArgsMergeStrategy.Merge

    protected fun valid(@ArgsMin(message = "请输入聊天内容", min = 1) args: MutableList<String>) {}

    @SentinelResource(
        SentinelRule.CHAT,
        entryType = EntryType.IN,
        fallback = "flow",
        exceptionsToIgnore = [GroupCmdException::class]
    )
    override fun command(args: MutableList<String>): MsgChain {
        self.valid(args)
        val (_, id, _) = ContextHolder.get()
        val prompt = args[0]
        val lock = RedisChatUtils.getGroupLockByGroupId(id)
        if (!lock) {
            RedisChatUtils.setGroupLockByGroupId(id, true)
            try {
                val queue = RedisChatUtils.getGroupQueueByGroupId(id)
                if (queue.isNotEmpty()) {
                    val totalTokens = queue.sumOf { it.second }
                    val nextTokens = totalTokens.div(queue.size.div(2))
                    while (OPENAI_MAX_TOKENS - totalTokens < nextTokens && queue.isNotEmpty()) {
                        queue.removeFirst()
                    }
                }
                val msg = buildList {
                    addAll(queue.map { (chatMessage, _) -> chatMessage })
                    add(ChatMessage(ChatMessageRole.USER.value(), prompt))
                }
                val chatCompletionResult = self.chatCompletion(msg)
                val chatMsg = chatCompletionResult.choices?.get(0)?.message?.content
                chatCompletionResult.usage!!.apply {
                    queue.add(ChatMessage(ChatMessageRole.USER.value(), prompt) to promptTokens)
                    queue.add(ChatMessage(ChatMessageRole.ASSISTANT.value(), chatMsg!!) to completionTokens)
                    RedisChatUtils.setGroupQueueByGroupId(id, queue)
                    return MsgChain(msgs = listOf(Msg(msg = "$chatMsg\n\ncost tokens: $totalTokens")))
                }
            } finally {
                RedisChatUtils.setGroupLockByGroupId(id, false)
            }
        } else {
            return MsgChain(msgs = listOf(Msg(msg = "发送慢一点，上一条消息还在回复中呢")))
        }
    }

    @Retryable(backoff = Backoff(delay = 1500))
    protected fun chatCompletion(msg: List<ChatMessage>): ChatCompletionResult {
        val httpEntity = HttpEntity(ChatCompletionRequest().apply {
//            model = "gpt-3.5-turbo"
            model = "gpt-3.5-turbo-0613"
            messages = msg
        }, HttpHeaders().apply {
            setBearerAuth(appProperty.openaiApiKey)
        })
        return restTemplate.exchange(CHAT_URL, HttpMethod.POST, httpEntity, ChatCompletionResult::class.java).body!!
    }

    @Recover
    protected fun fallback(e: RuntimeException, msg: List<ChatMessage>): ChatCompletionResult {
        throw GroupCmdException("网络代理连接失败，请稍后再试", e)
    }

    protected fun flow(args: MutableList<String>, e: BlockException) {
        throw GroupCmdException("消息发送太快，请稍后再试")
    }
}