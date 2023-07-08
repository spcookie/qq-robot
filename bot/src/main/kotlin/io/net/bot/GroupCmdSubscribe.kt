package io.net.bot

import io.net.api.command.GroupCmd
import io.net.api.command.GroupCmdException
import io.net.api.command.GroupCmdService
import io.net.api.command.MsgResult
import jakarta.validation.ConstraintViolationException
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import org.apache.dubbo.config.annotation.DubboReference
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *@author Augenstern
 *@date 2023/6/1
 */
@Component
class GroupCmdSubscribe(
    @DubboReference(check = false) val groupCmdService: GroupCmdService
) {

    private suspend fun GroupMessageEvent.call(cmd: String, vararg args: String): Result<MsgResult> {
        return runCatching {
            val groupCmd = GroupCmd.newBuilder()
                .setGroupId(group.id)
                .setBotId(bot.id)
                .setSenderId(sender.id)
                .setCmd(cmd)
                .addAllArgs(args.asList()).build()
            groupCmdService.invoke(groupCmd)
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

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(GroupCmdSubscribe::class.java)
    }

    inner class GroupListenerHost : ListenerHost {

        @EventHandler
        suspend fun GroupMessageEvent.onMessage() {
            getCmdAndArgs(message).also { (cmd, args) ->
                call(cmd, *args.toTypedArray()).onSuccess { result ->
                    val msg = result.msg
                    val bytes = result.bytes
                    if (msg != null && !bytes.isEmpty) {
                        val image = bytes.newInput().use {
                            it.uploadAsImage(sender)
                        }
                        group.sendMessage(message.quote() + msg + image)
                    } else if (msg != null) {
                        group.sendMessage(message.quote() + msg)
                    } else if (!bytes.isEmpty) {
                        val image = bytes.newInput().use {
                            it.uploadAsImage(sender)
                        }
                        group.sendMessage(message.quote() + image)
                    }
                }.onFailure {
                    when (it) {
                        is GroupCmdException -> {
                            group.sendMessage(message.quote() + "Warning: " + it.localizedMessage)
                        }

                        is ConstraintViolationException -> {
                            val msg = it.constraintViolations.map { cv -> cv.messageTemplate }
                            group.sendMessage(message.quote() + "Command error: " + msg)
                        }

                        else -> {
                            group.sendMessage(
                                message.quote() + "Unknown error: " + (it.message ?: it.cause.toString())
                            )
                        }
                    }
                }.exceptionOrNull()?.also {
                    if (it is GroupCmdException) {
                        if (it.cause != null) {
                            logger.error(it.message, it)
                        } else {
                            logger.warn(it.message)
                        }
                    } else {
                        logger.error(it.message, it)
                    }
                }
            }
        }
    }
}