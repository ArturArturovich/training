package ru.offenso

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object Spring : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    private const val ERR_MSG = "Spring utility class not initialized"
    private lateinit var context: ApplicationContext
    fun <T> bean(clazz: Class<T>): T = if (::context.isInitialized) context.getBean(clazz) else throw IllegalStateException(ERR_MSG)
}