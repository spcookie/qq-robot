package io.net.image.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

/**
 *@author Augenstern
 *@since 2023/7/27
 */
@ConfigurationProperties(prefix = "app")
@Component
class AppProperty {
    lateinit var pixivR18RecallIn: Duration
}