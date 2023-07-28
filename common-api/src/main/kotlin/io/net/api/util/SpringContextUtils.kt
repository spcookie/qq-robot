package io.net.api.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 *@author Augenstern
 *@since 2023/7/26
 */
@Component
class SpringContextUtils : ApplicationContextAware {

    companion object {
        lateinit var context: ApplicationContext

        inline fun <reified T> getBean(): T = context.getBean(T::class.java)

        inline fun <reified T> getBean(name: String): T = context.getBean(name, T::class.java)
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}