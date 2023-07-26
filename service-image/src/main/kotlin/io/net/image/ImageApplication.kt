package io.net.image

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling

@EnableDubbo
@EnableRetry
@EnableScheduling
@SpringBootApplication(scanBasePackages = ["io.net.*"])
class ImageApplication

fun main(args: Array<String>) {
    runApplication<ImageApplication>(*args)
}
