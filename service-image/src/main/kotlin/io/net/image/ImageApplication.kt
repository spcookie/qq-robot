package io.net.image

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableDubbo
@SpringBootApplication
class ImageApplication

fun main(args: Array<String>) {
    runApplication<ImageApplication>(*args)
}
