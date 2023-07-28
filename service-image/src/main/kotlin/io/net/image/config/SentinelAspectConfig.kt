package io.net.image.config

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 *@author Augenstern
 *@since 2023/7/28
 */
@Configuration
class SentinelAspectConfig {
    @Bean
    fun sentinelResourceAspect() = SentinelResourceAspect()
}