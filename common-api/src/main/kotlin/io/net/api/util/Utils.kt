package io.net.api.util

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *@author Augenstern
 *@since 2023/7/28
 */
class DeleteAfterUseLock<T : Any> {

    private val lockPoll = ConcurrentHashMap<T, ReentrantReadWriteLock>()

    class DeleteWrapper {
        var delete: (() -> Unit)? = null

        fun delete(block: () -> Unit) {
            delete = block
        }
    }

    fun <E> lock(id: T, resource: DeleteWrapper.() -> E): E {
        var deleted = false
        val lock = lockPoll.computeIfAbsent(id) {
            deleted = true
            ReentrantReadWriteLock()
        }
        lock.readLock().lock()
        try {
            val wrapper = DeleteWrapper()
            val result = resource(wrapper)
            if (deleted) {
                CompletableFuture.supplyAsync {
                    try {
                        lock.writeLock().lock()
                        wrapper.delete?.let { it() }
                    } finally {
                        lock.writeLock().unlock()
                        lockPoll.remove(id)
                    }
                }
            }
            return result
        } finally {
            lock.readLock().unlock()
        }
    }

}
