package ru.offenso.controllers.admin

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.offenso.Menu
import ru.offenso.controllers.BaseController

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('administer roles', 'administer users', 'administer permissions')")
class AdminController : BaseController() {
    @Menu("admin", 4, "administer roles", "administer users", "administer permissions")
    @GetMapping("", "/")
    fun index() = "admin/index"
}
