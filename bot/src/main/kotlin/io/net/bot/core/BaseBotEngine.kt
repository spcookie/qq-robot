package io.net.bot.core

import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.utils.BotConfiguration
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import kotlin.coroutines.CoroutineContext


/**
 *@author Augenstern
 *@date 2023/5/31
 */
@Component
class BaseBotEngine : CoroutineScope, BotEngine {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(BaseBotEngine::class.java)
    }

    private lateinit var bot: Bot

    @Async
    override fun start(qq: Long, password: String, protocol: String, handle: Bot.() -> Unit) {
        runBlocking {
            coroutineScope {
                this@BaseBotEngine.launch(this.coroutineContext) {
                    BotFactory.newBot(qq, password) {
                        this.protocol = when (protocol) {
                            "PHONE" -> BotConfiguration.MiraiProtocol.ANDROID_PHONE
                            "PAD" -> BotConfiguration.MiraiProtocol.ANDROID_PAD
                            "WATCH" -> BotConfiguration.MiraiProtocol.ANDROID_WATCH
                            "IPAD" -> BotConfiguration.MiraiProtocol.IPAD
                            "MACOS" -> BotConfiguration.MiraiProtocol.MACOS
                            else -> BotConfiguration.MiraiProtocol.ANDROID_PHONE
                        }
                        fileBasedDeviceInfo()
                        enableContactCache()
                        inheritCoroutineContext()
                    }.run {
                        this@BaseBotEngine.bot = this
                        try {
                            login()
                        } catch (_: RuntimeException) {
                            cancel()
                            close()
                        }
                        handle(this)
                    }
                }
            }
        }
    }

    @PreDestroy
    fun destroy() {
        if (::bot.isInitialized) {
            bot.close()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() +
                CoroutineName("Bot-Coroutine") +
                CoroutineExceptionHandler { _, e ->
                    logger.error(e.message, e)
                }
}