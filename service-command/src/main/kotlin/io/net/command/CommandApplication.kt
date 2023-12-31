package io.net.command

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 *@author Augenstern
 *@since 2023/7/6
 */
@EnableDubbo
@EnableConfigurationProperties(CmdProperty::class)
@SpringBootApplication
class CommandApplication

fun main(args: Array<String>) {
    runApplication<CommandApplication>(*args)
}