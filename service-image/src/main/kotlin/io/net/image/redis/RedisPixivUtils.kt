package io.net.image.redis

import io.net.api.util.SpringContextUtils
import io.net.image.bo.PixivRandomResultBO
import org.springframework.data.redis.core.RedisTemplate

/**
 *@author Augenstern
 *@since 2023/7/28
 */
object RedisPixivUtils {

    private const val KEY = "image:pixiv"

    private val ops
        get() = (SpringContextUtils.getBean<RedisTemplate<String, Any>>("redisTemplate") as RedisTemplate<String, PixivRandomResultBO>)
            .boundListOps(KEY)

    fun getPixiv(count: Long): List<PixivRandomResultBO>? {
        return ops.leftPop(count)
    }

    fun addPixiv(pixivRandomResultBOs: List<PixivRandomResultBO>) {
        ops.leftPushAll(*(pixivRandomResultBOs.toTypedArray()))
    }
}