package ru.offenso.controllers.admin

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.offenso.Menu
import ru.offenso.controllers.BaseController
import ru.offenso.entities.Permission
import ru.offenso.exceptions.NotFoundException
import ru.offenso.repositories.PermissionRepository

@Controller
@RequestMapping("/admin/permissions")
@PreAuthorize("hasAuthority('administer permissions')")
class AdminPermissionsController(
    var repo: PermissionRepository
) : BaseController() {
    companion object {
        const val PATH = "admin/permissions"
    }

    @Menu("permissions", 3, "administer permissions")
    @GetMapping("", "/")
    fun list(model: Model): String {
        model.addAttribute("models", repo.findAll())
        return "$PATH/list"
    }

    @GetMapping("/{id}")
    fun view(@PathVariable(value = "id") id: Long, model: Model): String {
        model.addAttribute("model", repo.findById(id).orElseThrow { NotFoundException() })
        return "$PATH/view"
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        model.addAttribute("model", Permission())
        return "$PATH/create"
    }

    @PostMapping("/create")
    fun createPost(
        @RequestParam("name") name: String,
        @RequestParam("description") description: String,
        model: Model
    ): String {
        repo.save(Permission(name, description))
        return "redirect:/admin/permissions"
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable(value = "id") id: Long, model: Model): String {
        model.addAttribute("model", repo.findById(id).orElseThrow { NotFoundException() })
        return "$PATH/edit"
    }

    @PostMapping("/{id}/edit")
    fun editPost(
        @PathVariable(value = "id") id: Long,
        @RequestParam("name") name: String,
        @RequestParam("description") description: String
    ): String {
        repo.save(repo.findById(id).orElseThrow { NotFoundException() }.also {
            it.name = name
            it.description = description
        })
        return "redirect:/admin/permissions"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable(value = "id") id: Long, model: Model): String {
        model.addAttribute("model", repo.findById(id).orElseThrow { NotFoundException() })
        return "$PATH/delete"
    }

    @PostMapping("/{id}/delete")
    fun deletePost(@PathVariable(value = "id") id: Long): String {
        repo.delete(repo.findById(id).orElseThrow { NotFoundException() })
        return "redirect:/admin/permissions"
    }
}
