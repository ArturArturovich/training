package ru.offenso.extensions

import org.springframework.security.core.context.SecurityContextHolder
import ru.offenso.models.Principal

fun securityPrincipal() = SecurityContextHolder.getContext().authentication?.run { if (isAuthenticated) principal as? Principal else null }
