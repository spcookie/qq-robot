package io.net.api.base

/**
 *@author Augenstern
 *@since 2023/7/29
 */
object ContextHolder {
    private val context: ThreadLocal<Context> = ThreadLocal()

    data class Context(
        val botId: Long,
        val groupId: Long,
        val senderId: Long
    )

    fun get(): Context = context.get()

    fun set(ctx: Context) = context.set(ctx)

    fun remove() = context.remove()

    fun <T> use(ctx: Context, block: () -> T): T {
        set(ctx)
        val r = block()
        remove()
        return r
    }
}