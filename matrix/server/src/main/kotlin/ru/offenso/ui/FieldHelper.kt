package ru.offenso.ui

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

@Service
class FieldHelper(@Qualifier("messageSource") val t: MessageSource) {
    fun build(obj: Any, locale: Locale) = obj::class.memberProperties.flatMap { f ->
        val textareaList = f.findAnnotation<TextareaField>()?.let { a ->
            val help = if (a.help.isNotEmpty()) t.getMessage(a.help, arrayOf(), locale) else a.help
            listOf(
                FormGroupTextarea(
                    a.order,
                    obj::class.simpleName.orEmpty(),
                    t.getMessage(a.title, arrayOf(), locale),
                    t.getMessage(a.placeholder, arrayOf(), locale),
                    f.name,
                    f.getter.call(obj),
                    help,
                    a.rows
                )
            )
        } ?: emptyList()
        val inputList = f.findAnnotation<InputField>()?.let { a ->
            val help = if (a.help.isNotEmpty()) t.getMessage(a.help, arrayOf(), locale) else a.help
            listOf(
                FormGroupInput(
                    a.order,
                    obj::class.simpleName.orEmpty(),
                    t.getMessage(a.title, arrayOf(), locale),
                    t.getMessage(a.placeholder, arrayOf(), locale),
                    a.type,
                    f.name,
                    f.getter.call(obj),
                    help,
                    a.required
                )
            )
        } ?: emptyList()
        val selectList = f.findAnnotation<SelectField>()?.let { a ->
            val help = if (a.help.isNotEmpty()) t.getMessage(a.help, arrayOf(), locale) else a.help
            listOf(
                FormGroupSelect(
                    a.order,
                    obj::class.simpleName.orEmpty(),
                    t.getMessage(a.title, arrayOf(), locale),
                    t.getMessage(a.placeholder, arrayOf(), locale),
                    f.name,
                    f.getter.call(obj),
                    help,
                    a.required,
                    a.options.map { it to it }.toMap()
                )
            )
        } ?: emptyList()
        inputList + textareaList + selectList
    }.sortedBy { it.order }
}