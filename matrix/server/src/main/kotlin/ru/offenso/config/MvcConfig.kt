package ru.offenso.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import ru.offenso.properties.AppProperties

@Configuration
class MvcConfig(
    private val props: AppProperties
) : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) = mapOf(
        "/images/**" to props.paths.images
    ).forEach { (res, loc) ->
        registry.addResourceHandler(res).addResourceLocations("file://$loc/")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LocaleChangeInterceptor().apply {
            paramName = "lang"
        })
    }
}