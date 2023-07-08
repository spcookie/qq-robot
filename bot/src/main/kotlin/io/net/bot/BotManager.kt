package io.net.bot

import net.mamoe.mirai.Bot

/**
 *@author Augenstern
 *@date 2023/6/1
 */
interface BotManager {
    fun start(qq: Long, password: String, handle: Bot.() -> Unit)
}