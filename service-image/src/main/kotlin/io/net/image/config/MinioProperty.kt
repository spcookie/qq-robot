package io.net.image.config

import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.properties.Delegates

/**
 *@author Augenstern
 *@since 2023/7/26
 */
@ConfigurationProperties(prefix = "minio")
class MinioProperty {

    lateinit var host: String

    var port: Int by Delegates.notNull()

    var accessKey: String by Delegates.notNull()

    var secretKey: String by Delegates.notNull()

}