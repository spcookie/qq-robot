package io.net.image.redis

import io.net.api.util.SpringContextUtils
import io.net.image.bo.PixivRandomResult
import org.springframework.data.redis.core.RedisTemplate

/**
 *@author Augenstern
 *@since 2023/7/28
 */
object RedisPixivUtils {

    private const val KEY = "image:pixiv"

    private val ops
        get() = SpringContextUtils.getBean<RedisTemplate<String, PixivRandomResult>>("redisTemplate")
            .boundListOps(KEY)

    fun getPixiv(count: Long): List<PixivRandomResult>? {
        return ops.leftPop(count)
    }

    fun addPixiv(pixivRandomResults: List<PixivRandomResult>) {
        ops.leftPushAll(*(pixivRandomResults.toTypedArray()))
    }
}