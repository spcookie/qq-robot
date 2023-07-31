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
import net.mamoe.mirai.mock.contact.MockNormalMember
import net.mamoe.mirai.mock.utils.simpleMemberInfo
import net.mamoe.mirai.utils.LoggerAdapters
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@Suppress("UNUSED")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class BotApplicationTests {

    @Test
    @DisplayName("不存在的命令")
    fun command_that_not_exist() {
        blockMock("command_that_not_exist")
    }

    @Test
    @DisplayName("img")
    fun img() {
        blockMock("img")
    }

    @ParameterizedTest
    @DisplayName("带参数的st")
    @ValueSource(strings = ["胡桃", "JOJO"])
    fun st_param(kw: String) {
        blockMock("st $kw")
    }

    @Test
    @DisplayName("st")
    fun st() {
        blockMock("st")
    }

    @Test
    @DisplayName("img限流")
    fun img_flow() {
        runBlocking {
            repeat(4) {
                launch {
                    mock("img")
                }
            }
        }
    }

    @Test
    @DisplayName("没有参数的chat")
    fun chat_not_para() {
        blockMock("chat")
    }

    @ParameterizedTest
    @DisplayName("chat")
    @ValueSource(strings = ["你好！"])
    fun chat(msg: String) {
        blockMock("chat $msg")
    }

    @Test
    @DisplayName("chat限流")
    fun chat_flow() {
        runBlocking {
            repeat(3) {
                launch {
                    mock("chat")
                }
            }
        }
    }

    @set:Autowired
    lateinit var groupCmdSubscribe: GroupCmdSubscribe

    @MockBean
    lateinit var botRunner: BotRunner

    lateinit var bot: MockBot

    lateinit var member: MockNormalMember

    val context = SupervisorJob() +
            CoroutineName("MockBot-Coroutine") +
            CoroutineExceptionHandler { _, e ->
                e.printStackTrace()
            }

    suspend fun mock(msg: String) {
        var flag = true
        try {
            member.says(At(bot) + PlainText(msg))
        } catch (_: Exception) {
            flag = false
        }
        Assertions.assertTrue(flag, "命令'$msg'测试失败")
    }

    fun blockMock(msg: String) {
        runBlocking {
            mock(msg)
        }
    }

    @OptIn(LowLevelApi::class)
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
                            member = bot.addGroup(666, "testing!")
                                .addMember(simpleMemberInfo(5971, "test", permission = MemberPermission.OWNER))
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
