package io.net.bot.core

import io.net.bot.subscribe.GroupCmdSubscribe
import kotlinx.coroutines.*
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.LoggerAdapters
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import xyz.cssxsh.mirai.tool.FixProtocolVersion
import xyz.cssxsh.mirai.tool.KFCFactory
import java.util.*

@Component
class BotRunner(
    private val botEngine: BotEngine, private var groupCmdSubscribe: GroupCmdSubscribe
) : CommandLineRunner {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(BotRunner::class.java)
    }

    @Value("\${bot.qq:#{null}}")
    private var qq: Long? = null

    @Value("\${bot.password:#{null}}")
    private var password: String? = null

    @Value("\${bot.protocol:#{null}}")
    private var protocol: String? = null

    @Value("\${bot.fix:false}")
    private var fix: Boolean = false

    override fun run(vararg args: String) {
        LoggerAdapters.useLog4j2()
        logger.info("机器人开始启动...")
        manager()
    }

    fun manager() {
        val qq = qq
        val password = password
        val protocol = protocol
        if (qq == null) {
            logger.error("请输入QQ账号")
        } else if (password == null) {
            logger.error("请输入密码")
        } else if (protocol == null) {
            logger.error("请选择协议")
        } else {
            fixProtocol(fix, protocol)
            botEngine.start(qq, password, protocol) {
                globalEventChannel().registerListenerHost(groupCmdSubscribe.GroupListenerHost())
            }
        }
    }

    private fun fixProtocol(enable: Boolean, protocol: String) {
        if (enable) {
            if (protocol != "PHONE") {
                logger.error("qsign签名服务只适用于Android")
                return
            }
            try {
                FixProtocolVersion.load(BotConfiguration.MiraiProtocol.ANDROID_PHONE)
            } catch (_: Exception) {
                FixProtocolVersion.fetch(BotConfiguration.MiraiProtocol.ANDROID_PHONE, "8.9.63")
            }
        }
        val info = FixProtocolVersion.info()
        val formatInfo = buildString {
            appendLine("当前各协议版本：")
            for ((_, str) in info) {
                appendLine(str)
            }
        }
        logger.info(formatInfo)
        if (enable) {
            KFCFactory.install()
        }
    }
}