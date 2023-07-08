package io.net.bot

import kotlinx.coroutines.*
import net.mamoe.mirai.LowLevelApi
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.mock.MockBotFactory
import net.mamoe.mirai.mock.utils.simpleMemberInfo
import net.mamoe.mirai.utils.LoggerAdapters
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class BotRunner(
    private val botManager: BotManager, private var groupCmdSubscribe: GroupCmdSubscribe
) : CommandLineRunner {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(BotRunner::class.java)
    }

//    @Value("\${bot.qq}")
//    private var qq: Long? = null
//
//    @Value("\${bot.password}")
//    private val password: String? = null

    override fun run(vararg args: String) {
        LoggerAdapters.useLog4j2()
        logger.info("机器人开始启动...")
        manager()
    }

    fun manager() {
//        val qq = qq
//        val password = password
//        if (qq == null) {
//            logger.error("请输入QQ账号")
//        } else if (password == null) {
//            logger.error("请输入密码")
//        } else {
//            botManager.start(qq, password) {
//                globalEventChannel().registerListenerHost(groupCmdSubscribe.GroupListenerHost())
//            }
//        }
        mock()
    }

    @OptIn(DelicateCoroutinesApi::class, LowLevelApi::class)
    fun mock() {
        runBlocking {
            GlobalScope.launch {
                MockBotFactory.newMockBotBuilder().id(1307083930).nick("augenstern-bot").create().run {
                    try {
                        login()
                        globalEventChannel().registerListenerHost(groupCmdSubscribe.GroupListenerHost())
                        val member = addGroup(666, "testing!").addMember(
                            simpleMemberInfo(uin = 5971, name = "test", permission = MemberPermission.OWNER)
                        )
                        println("请输入指令...")
                        val scanner = Scanner(System.`in`)
                        while (true) {
                            val msg = scanner.nextLine()
                            member.says(At(this) + PlainText(msg))
                        }
                    } catch (_: RuntimeException) {
                        cancel()
                        close()
                    }
                }
            }
        }
    }
}