package io.net.image.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.net.InetSocketAddress
import java.net.Proxy

/**
 *@author Augenstern
 *@since 2023/7/8
 */
@Configuration(proxyBeanMethods = false)
class RestTemplateConfig {
    @Bean
    fun direct(): RestTemplate {
        val restTemplate = RestTemplate()
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

    @Value("\${rest.host}")
    var host: String = ""

    @Value("\${rest.port}")
    var port: Int = 0

    @Bean
    fun proxy(): RestTemplate {
        val restTemplate = RestTemplate()
        val factory = SimpleClientHttpRequestFactory()
        factory.setProxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(host, port)))
        restTemplate.requestFactory = factory
        return restTemplate
    }
}