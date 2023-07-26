package io.net.bot.subscribe

import io.net.api.GroupCmd
import io.net.api.MsgCode
import io.net.api.MsgResult
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

    private suspend fun GroupMessageEvent.call(ats: List<Long>, cmd: String, vararg args: String): Result<MsgResult> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val groupCmd = GroupCmd.newBuilder()
                    .addAllAts(ats)
                    .setGroupId(group.id)
                    .setBotId(bot.id)
                    .setSenderId(sender.id)
                    .setCmd(cmd.uppercase())
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
                val command = (if (index == -1) content else content.substring(0 until index)).lowercase()
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
                call(getAts(message), cmd, *args.toTypedArray()).onSuccess { result ->
                    val code = result.code!!
                    val msg = result.msg
                    val bytes = result.bytes
                    val foot = result.foot
                    when (code) {
                        MsgCode.OK -> {
                            if (msg.isNotBlank() && !bytes.isEmpty) {
                                val image = bytes.newInput().use {
                                    it.uploadAsImage(sender)
                                }
                                group.sendMessage(message.quote() + msg + image + foot)
                            } else if (msg.isNotBlank()) {
                                group.sendMessage(message.quote() + msg + foot)
                            } else if (!bytes.isEmpty) {
                                val image = bytes.newInput().use {
                                    it.uploadAsImage(sender)
                                }
                                group.sendMessage(message.quote() + image + foot)
                            }
                        }

                        MsgCode.BUSINESS_ANOMALY -> group.sendMessage(message.quote() + msg + foot)
                        MsgCode.RPC_ANOMALY -> group.sendMessage(message.quote() + msg)
                        MsgCode.UNRECOGNIZED -> logger.error("MsgCode.UNRECOGNIZED: " + result.unknownFields.toString())
                    }
                }.onFailure {
                    logger.error(it.message, it)
                }
            }
        }
    }
}