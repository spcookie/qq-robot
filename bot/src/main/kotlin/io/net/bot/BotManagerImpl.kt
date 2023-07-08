package io.net.bot

import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.utils.BotConfiguration
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy
import kotlin.coroutines.CoroutineContext


/**
 *@author Augenstern
 *@date 2023/5/31
 */
@Component
class BotManagerImpl : CoroutineScope, BotManager {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(BotManagerImpl::class.java)
    }

    private lateinit var bot: Bot

    @Async
    override fun start(qq: Long, password: String, handle: Bot.() -> Unit) {
        runBlocking {
            coroutineScope {
                this@BotManagerImpl.launch(this.coroutineContext) {
                    BotFactory.newBot(qq, password) {
                        protocol = BotConfiguration.MiraiProtocol.MACOS
                        fileBasedDeviceInfo()
                        enableContactCache()
                        inheritCoroutineContext()
                    }.run {
                        this@BotManagerImpl.bot = this
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