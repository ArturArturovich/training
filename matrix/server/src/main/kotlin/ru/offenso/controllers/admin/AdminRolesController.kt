package ru.offenso.controllers.admin

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.offenso.Menu
import ru.offenso.controllers.BaseController
import ru.offenso.entities.Role
import ru.offenso.exceptions.NotFoundException
import ru.offenso.repositories.PermissionRepository
import ru.offenso.repositories.RoleRepository

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasAuthority('administer roles')")
class AdminRolesController(
    var repo: RoleRepository,
    var permRepo: PermissionRepository
) : BaseController() {
    companion object {
        const val PATH = "admin/roles"
    }

    @Menu("roles", 2, "administer roles")
    @GetMapping("", "/")
    fun list(model: Model): String {
        model.addAttribute("models", repo.findAll())
        return "$PATH/list"
    }

    @GetMapping("/{id}")
    fun view(
        @PathVariable(value = "id") id: Long,
        model: Model
    ): String {
        val it = repo.findById(id).orElseThrow { NotFoundException() }
        model.addAttribute("model", it)
        return "$PATH/view"
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        model.addAttribute("model", Role())
        model.addAttribute("permissions", permRepo.findAll())
        return "$PATH/create"
    }

    @PostMapping("/create")
    fun createPost(
        @RequestParam("name") name: String,
        @RequestParam("displayName") displayName: String,
        @RequestParam("description") description: String,
        @RequestParam("permissions", required = false) permIds: List<Long>?,
        model: Model
    ): String {
        repo.save(Role(name, displayName, description).apply {
            permIds?.let { permissions.addAll(permRepo.findAllById(it)) }
        })
        return "redirect:/admin/roles"
    }

    @GetMapping("/{id}/edit")
    fun edit(
        @PathVariable(value = "id") id: Long,
        model: Model
    ): String {
        model.addAttribute("model", repo.findById(id).orElseThrow { NotFoundException() })
        model.addAttribute("permissions", permRepo.findAll())
        return "$PATH/edit"
    }

    @PostMapping("/{id}/edit")
    fun editPost(
        @PathVariable(value = "id") id: Long,
        @RequestParam("name") name: String,
        @RequestParam("displayName") displayName: String,
        @RequestParam("description") description: String,
        @RequestParam("permissions", required = false) permIds: List<Long>?
    ): String {
        repo.save(repo.findById(id).orElseThrow { NotFoundException() }.also {
            it.name = name
            it.displayName = displayName
            it.description = description
            it.permissions.clear()
            permIds?.let { ids -> it.permissions.addAll(permRepo.findAllById(ids)) }
        })
        return "redirect:/admin/roles"
    }

    @GetMapping("/{id}/delete")
    fun delete(
        @PathVariable(value = "id") id: Long,
        model: Model
    ): String {
        model.addAttribute("model", repo.findById(id).orElseThrow { NotFoundException() })
        return "$PATH/delete"
    }

    @PostMapping("/{id}/delete")
    fun deletePost(
        @PathVariable(value = "id") id: Long
    ): String {
        repo.delete(repo.findById(id).orElseThrow { NotFoundException() })
        return "redirect:/admin/roles"
    }
}
