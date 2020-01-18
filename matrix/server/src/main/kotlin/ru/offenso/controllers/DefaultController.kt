package ru.offenso.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.offenso.models.Principal

@Controller
@RequestMapping("", "/")
class DefaultController : BaseController() {
    @GetMapping("", "/")
    fun home(@AuthenticationPrincipal principal: Principal?) = if (principal != null) "redirect:/nodes" else "redirect:/login"
}
