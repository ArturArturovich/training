package ru.offenso.services

import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import ru.offenso.Menu
import ru.offenso.MenuItem
import ru.offenso.extensions.securityPrincipal
import java.util.*

@Service
class MenuService(
    @Qualifier("messageSource") val t: MessageSource
) : ApplicationContextAware {
    private lateinit var ctx: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ctx = applicationContext
    }

    @ModelAttribute("menu")
    fun menu() = menu

    fun sorted(menu: Map<String, MenuItem>): MutableMap<String, MenuItem> = menu
        .map {
            it.key to MenuItem(
                it.value.title,
                it.value.url,
                it.value.authority,
                it.value.order,
                sorted(it.value.children)
            )
        }
        .sortedBy { it.second.order }
        .toMap()
        .toMutableMap()

    private val menu by lazy {
        val menu = mutableMapOf<String, MenuItem>()
        ctx.beanDefinitionNames.flatMap { beanName ->
            val obj = ctx.getBean(beanName)
            val objClz = if (AopUtils.isAopProxy(obj)) AopUtils.getTargetClass(obj) else obj.javaClass
            objClz.declaredMethods.filter { it.isAnnotationPresent(Menu::class.java) }.map { m ->
                val classPath = objClz.getAnnotation(RequestMapping::class.java)?.value?.firstOrNull() ?: ""
                val methodPath = m.getAnnotation(GetMapping::class.java)?.value?.firstOrNull() ?: ""
                val url = classPath + methodPath
                url to m.getAnnotation(Menu::class.java)
            }
        }.sortedBy { it.first }.forEach { m ->
            val annotation = m.second
            var scope: MutableMap<String, MenuItem>? = menu
            m.first.split("/").forEach { i ->
                if (i != "" && scope != null) {
                    if (scope!!.containsKey(i)) {
                        scope = scope!![i]?.children
                    } else scope!![i] = MenuItem(annotation.title, m.first, annotation.authority, annotation.order)
                }
            }
        }
        sorted(menu).toMap()
    }

    fun renderMenu(url: String?, locale: Locale) = render(menu, url, locale, securityPrincipal()?.authorities?.map { it.authority }?.toSet().orEmpty())

    private fun render(
        menu: Map<String, MenuItem>,
        key: String?,
        locale: Locale,
        authorities: Set<String>?
    ): Map<String, I> = menu
        .filter { authorities != null && (it.value.authority.isEmpty() || it.value.authority.toSet().intersect(authorities).isNotEmpty()) }
        .map {
            it.key to I(
                try {
                    t.getMessage(it.value.title, arrayOf(), locale)
                } catch (e: Exception) {
                    it.value.title
                },
                it.value.url,
                it.value.url == key,
                render(it.value.children, key, locale, authorities)
            )
        }.toMap()

    class I(
        var title: String,
        var url: String,
        var active: Boolean,
        var children: Map<String, I>
    )
}
