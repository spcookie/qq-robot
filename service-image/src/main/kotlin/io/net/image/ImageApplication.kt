package io.net.image

import io.net.image.config.AppProperty
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling

@EnableDubbo
@EnableRetry
@EnableScheduling
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperty::class)
@SpringBootApplication(scanBasePackages = ["io.net.*"])
class ImageApplication

fun main(args: Array<String>) {
    runApplication<ImageApplication>(*args)
}