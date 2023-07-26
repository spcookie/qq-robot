package io.net.text

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableDubbo
@SpringBootApplication
class TextApplication

fun main(args: Array<String>) {
    runApplication<TextApplication>(*args)
}
