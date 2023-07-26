package io.net.image.config

import io.minio.MinioClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *@author Augenstern
 *@since 2023/7/26
 */
@EnableConfigurationProperties(MinioProperty::class)
@Configuration
class MinioConfig(val minioProperty: MinioProperty) {
    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint(minioProperty.host, minioProperty.port, false)
        .credentials(minioProperty.accessKey, minioProperty.secretKey)
        .build()
}