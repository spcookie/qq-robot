package io.net.image.minio

import io.minio.*
import io.net.api.util.SpringContextUtils

/**
 *@author Augenstern
 *@since 2023/7/26
 */
object MinioImageUtils {

    private const val BUCKET = "image"

    private val minio: MinioClient
        get() = SpringContextUtils.getBean<MinioClient>()

    private var bucketExists = false

    fun getImage(path: String): ByteArray {
        val args = GetObjectArgs.builder()
            .bucket(BUCKET)
            .`object`(path)
            .build()
        return minio.getObject(args).use { it.readBytes() }
    }

    fun putImage(path: String, byteArray: ByteArray) {
        ensureBucketExists()
        val args = PutObjectArgs.builder()
            .bucket(BUCKET)
            .`object`(path)
            .stream(byteArray.inputStream(), -1, ObjectWriteArgs.MIN_MULTIPART_SIZE.toLong())
            .build()
        minio.putObject(args)
    }

    private fun ensureBucketExists() {
        if (!bucketExists) {
            synchronized(MinioImageUtils::class) {
                if (!bucketExists) {
                    bucketExists = minio.bucketExists(BucketExistsArgs.builder().bucket(BUCKET).build())
                    if (!bucketExists) {
                        minio.makeBucket(MakeBucketArgs.builder().bucket(BUCKET).build())
                        bucketExists = true
                    }
                }
            }
        }
    }

    fun removeImage(path: String) {
        val args = RemoveObjectArgs.builder()
            .bucket(BUCKET)
            .`object`(path)
            .build()
        minio.removeObject(args)
    }
}