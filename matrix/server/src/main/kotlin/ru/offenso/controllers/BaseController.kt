package ru.offenso.controllers

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import ru.offenso.Spring
import ru.offenso.models.Principal

abstract class BaseController {
    @ModelAttribute("lang")
    fun lang() = LocaleContextHolder.getLocale().language ?: "ru"
}