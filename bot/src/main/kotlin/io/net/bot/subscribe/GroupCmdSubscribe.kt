package io.net.bot.subscribe

import io.net.api.GroupCmd
import io.net.api.MsgResult
import io.net.api.MsgResultChain
import io.net.api.command.GroupCmdService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import org.apache.dubbo.common.constants.ClusterRules
import org.apache.dubbo.common.constants.LoadbalanceRules
import org.apache.dubbo.config.annotation.DubboReference
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *@author Augenstern
 *@date 2023/6/1
 */
@Component
class GroupCmdSubscribe(
    @DubboReference(
        check = false,
        loadbalance = LoadbalanceRules.ROUND_ROBIN,
        cluster = ClusterRules.FAIL_OVER,
        timeout = 1000 * 60 * 2,
        mock = "true"
    ) private val groupCmdService: GroupCmdService
) {

    private suspend fun GroupMessageEvent.call(
        ats: List<Long>,
        cmd: String,
        vararg args: String
    ): Result<MsgResultChain> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val groupCmd = GroupCmd.newBuilder()
                    .addAllAts(ats)
                    .setGroupId(group.id)
                    .setBotId(bot.id)
                    .setSenderId(sender.id)
                    .setCmd(cmd)
                    .addAllArgs(args.asList()).build()
                groupCmdService.invoke(groupCmd)
            }
        }
    }

    private fun getCmdAndArgs(message: MessageChain): Pair<String, List<String>> {
        message.filterIsInstance<PlainText>().forEach {
            val content = it.content.trim()
            if (content.isNotBlank()) {
                val index = content.indexOf(' ')
                val command = (if (index == -1) content else content.substring(0 until index))
                val args = if (index == -1) {
                    emptyList()
                } else {
                    val substring = content.substring(index + 1).trim()
                    substring.split(' ')
                }
                return command to args
            }
        }
        return "" to listOf()
    }

    private fun getAts(message: MessageChain): List<Long> {
        return message.filterIsInstance<At>().map {
            it.target
        }
    }

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(GroupCmdSubscribe::class.java)
    }

    inner class GroupListenerHost : ListenerHost {

        @EventHandler
        suspend fun GroupMessageEvent.onMessage() {
            getCmdAndArgs(message).also { (cmd, args) ->
                call(getAts(message), cmd, *args.toTypedArray())
                    .onSuccess { results -> send(results) }
            }
        }

        private suspend fun GroupMessageEvent.send(list: MsgResultChain) {
            if (list.msgResultCount > 0) {
                val default = list.getMsgResult(0)
                when (list.code!!) {
                    MsgResultChain.Code.OK -> sendOkMessage(list)
                    MsgResultChain.Code.BUSINESS_ANOMALY -> group.sendMessage(message.quote() + "•́‸ก " + default.msg)
                    MsgResultChain.Code.RPC_ANOMALY -> group.sendMessage(message.quote() + "＞﹏＜" + default.msg)
                    MsgResultChain.Code.UNRECOGNIZED -> logger.error("UNRECOGNIZED: " + default.unknownFields.toString())
                }
            }
        }

        private suspend fun GroupMessageEvent.sendOkMessage(list: MsgResultChain) {
            val foot = list.metaMap["foot"].toString()
            val messages = list.msgResultList
            val messageChain = buildMessageChain {
                messages.forEach { result ->
                    add(message.quote())
                    val msg = result.msg
                    val data = result.data
                    val mediaType = data.type
                    val bytes = data.bytes.toByteArray()
                    if (msg.isNotBlank()) {
                        add(msg)
                    }
                    if (bytes.isNotEmpty()) {
                        // TODO: audio not implemented
                        if (mediaType == MsgResult.Data.MediaType.PICTURE) {
                            add(bytes.inputStream().use { it.uploadAsImage(sender) })
                        }
                    }
                }
                add(foot)
            }
            val mr = group.sendMessage(messageChain)
            val receipt = list.receipt
            val recall = receipt.recall
            val fallback = receipt.fallback
            if (recall > 0) {
                mr.recallIn(recall.toLong())
            }
            val ids = mr.source.ids
            if (ids.isEmpty() || ids.any { it < 0 }) {
                logger.warn("消息发送失败，账号可能已被风控")
                if (fallback.isNotBlank()) {
                    val fallbackMsg = group.sendMessage(message.quote() + fallback + foot)
                    if (recall > 0) {
                        fallbackMsg.recallIn(recall.toLong())
                    }
                }
            }
        }
    }
}