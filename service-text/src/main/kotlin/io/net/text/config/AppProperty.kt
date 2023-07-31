package io.net.text.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 *@author Augenstern
 *@since 2023/7/27
 */
@ConfigurationProperties(prefix = "app")
@Component
class AppProperty {
    lateinit var openaiApiKey: String
}