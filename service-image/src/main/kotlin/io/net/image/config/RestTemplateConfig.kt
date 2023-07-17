package io.net.image.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

/**
 *@author Augenstern
 *@since 2023/7/8
 */
@Configuration(proxyBeanMethods = false)
class RestTemplateConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        MappingJackson2HttpMessageConverter()
        val messageConverters = restTemplate.messageConverters
        for (messageConverter in messageConverters) {
            if (messageConverter is MappingJackson2HttpMessageConverter) {
                messageConverter.supportedMediaTypes = buildList {
                    addAll(messageConverter.supportedMediaTypes)
                    add(MediaType.valueOf("text/json;charset=UTF-8"))
                }
            }
        }
        return restTemplate
    }
}