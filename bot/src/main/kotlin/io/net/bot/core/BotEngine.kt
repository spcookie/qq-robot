package io.net.bot.core

import net.mamoe.mirai.Bot

/**
 *@author Augenstern
 *@date 2023/6/1
 */
interface BotEngine {
    fun start(qq: Long, password: String, protocol: String, handle: Bot.() -> Unit)
}