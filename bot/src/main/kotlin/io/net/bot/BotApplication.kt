package io.net.bot

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableDubbo
@EnableAsync
@SpringBootApplication
class BotApplication

fun main(args: Array<String>) {
    runApplication<BotApplication>(*args)
}
