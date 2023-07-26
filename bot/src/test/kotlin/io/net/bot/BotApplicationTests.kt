package io.net.bot

import io.net.bot.core.BotRunner
import io.net.bot.subscribe.GroupCmdSubscribe
import kotlinx.coroutines.*
import net.mamoe.mirai.LowLevelApi
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.mock.MockBot
import net.mamoe.mirai.mock.MockBotFactory
import net.mamoe.mirai.mock.utils.simpleMemberInfo
import net.mamoe.mirai.utils.LoggerAdapters
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@Suppress("UNUSED")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class BotApplicationTests {

    @Test
    @DisplayName("测试img")
    fun img() {
        mock("img")
    }

    @set:Autowired
    lateinit var groupCmdSubscribe: GroupCmdSubscribe

    @MockBean
    lateinit var botRunner: BotRunner

    lateinit var bot: MockBot

    val context = SupervisorJob() +
            CoroutineName("MockBot-Coroutine") +
            CoroutineExceptionHandler { _, e ->
                e.printStackTrace()
            }

    @OptIn(LowLevelApi::class)
    fun mock(msg: String) {
        runBlocking {
            val member = bot.addGroup(666, "testing!")
                .addMember(simpleMemberInfo(5971, "test", permission = MemberPermission.OWNER))
            member.says(At(bot) + PlainText(msg))
        }
    }

    @BeforeAll
    fun mockBot() {
        LoggerAdapters.useLog4j2()
        runBlocking {
            CoroutineScope(context).launch(this.coroutineContext) {
                MockBotFactory.newMockBotBuilder()
                    .id(1307083930)
                    .nick("augenstern-bot")
                    .create()
                    .run {
                        this@BotApplicationTests.bot = this
                        try {
                            login()
                        } catch (_: RuntimeException) {
                            cancel()
                            close()
                        }
                        globalEventChannel().registerListenerHost(groupCmdSubscribe.GroupListenerHost())
                    }
            }
        }
    }
}
