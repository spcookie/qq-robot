package io.net.text.redis

import io.net.api.util.SpringContextUtils
import io.net.text.bo.ChatMessage
import org.springframework.data.redis.core.RedisTemplate

/**
 *@author Augenstern
 *@since 2023/7/30
 */
object RedisChatUtils {
    private const val KEY = "text:chat"

    private val opsQueue
        get() = SpringContextUtils.getBean<RedisTemplate<String, Map<Long, MutableList<Pair<ChatMessage, Long>>>>>("redisTemplate")
            .boundHashOps<Long, MutableList<Pair<ChatMessage, Long>>>("$KEY:queue")

    private val opsLock
        get() = SpringContextUtils.getBean<RedisTemplate<String, Boolean>>("redisTemplate")
            .boundHashOps<Long, Boolean>("$KEY:lock")

    fun getGroupQueueByGroupId(id: Long) = opsQueue.get(id) ?: mutableListOf()
    fun getGroupLockByGroupId(id: Long) = opsLock.get(id) ?: false
    fun setGroupLockByGroupId(id: Long, lock: Boolean) = opsLock.put(id, lock)
    fun setGroupQueueByGroupId(id: Long, queue: MutableList<Pair<ChatMessage, Long>>) = opsQueue.put(id, queue)
}