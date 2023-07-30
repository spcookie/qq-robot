package io.net.image.service

import io.net.api.base.AbstractCmd
import io.net.api.base.CommonWorkServiceImpl
import io.net.api.enum.ServiceGroup
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *@author Augenstern
 *@since 2023/7/30
 */
@Configuration(proxyBeanMethods = false)
class WorkServiceConfig {
    @Bean
    @DubboService(group = ServiceGroup.IMAGE, filter = ["ex"])
    fun imageService(cmds: List<AbstractCmd>) = CommonWorkServiceImpl(cmds)
}