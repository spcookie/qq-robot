package io.net.text

import io.net.text.config.AppProperty
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@EnableDubbo
@EnableRetry
@EnableConfigurationProperties(AppProperty::class)
@SpringBootApplication(scanBasePackages = ["io.net.*"])
class TextApplication

fun main(args: Array<String>) {
    runApplication<TextApplication>(*args)
}
