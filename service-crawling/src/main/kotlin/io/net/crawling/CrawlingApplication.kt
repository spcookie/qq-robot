package io.net.crawling

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableDubbo
@SpringBootApplication
class CrawlingApplication

fun main(args: Array<String>) {
    runApplication<CrawlingApplication>(*args)
}
