package ru.offenso.controllers.admin

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.offenso.Menu
import ru.offenso.controllers.BaseController
import ru.offenso.entities.User
import ru.offenso.exceptions.NotFoundException
import ru.offenso.repositories.RoleRepository
import ru.offenso.repositories.UserRepository

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('administer users')")
class AdminUsersController(
    var passwordEncoder: PasswordEncoder,
    var repo: UserRepository,
    var roleRepo: RoleRepository
) : BaseController() {
    companion object {
        const val PATH = "admin/users"
    }

    @Menu("users", 1, "administer users")
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
        model.addAttribute("model", User())
        model.addAttribute("roles", roleRepo.findAll())
        return "$PATH/create"
    }

    @PostMapping("/create")
    fun createPost(
        @RequestParam("username") username: String,
        @RequestParam("email") email: String,
        @RequestParam("new-password") newPassword: String,
        @RequestParam("repeat-password") repeatPassword: String,
        @RequestParam("roles", required = false) roleIds: List<Long>?,
        @RequestParam("enabled", required = false, defaultValue = "false") enabled: Boolean,
        model: Model
    ) = if (newPassword.isNotEmpty() && repeatPassword.isNotEmpty() && newPassword == repeatPassword) {
        val user = repo.save(User(email, username, passwordEncoder.encode(newPassword), enabled).apply {
            roleIds?.let { roles.addAll(roleRepo.findAllById(it)) }
        })
        "redirect:/admin/users/${user.id}"
    } else {
        model.addAttribute("model", User(email, username, "", enabled).apply {
            roleIds?.let { roles.addAll(roleRepo.findAllById(it)) }
        })
        "$PATH/create"
    }

    @GetMapping("/{id}/edit")
    fun edit(
        @PathVariable(value = "id") id: Long,
        model: Model
    ): String {
        model.addAttribute("model", repo.findById(id).orElseThrow { NotFoundException() })
        model.addAttribute("roles", roleRepo.findAll())
        return "$PATH/edit"
    }

    @PostMapping("/{id}/edit")
    fun editPost(
        @PathVariable(value = "id") id: Long,
        @RequestParam("username") username: String,
        @RequestParam("email") email: String,
        @RequestParam("new-password") newPassword: String,
        @RequestParam("repeat-password") repeatPassword: String,
        @RequestParam("roles", required = false) roleIds: List<Long>?,
        @RequestParam("enabled", required = false, defaultValue = "false") enabled: Boolean
    ): String {
        val it = repo.findById(id).orElseThrow { NotFoundException() }.also {
            it.username = username
            it.email = email
            if (newPassword.isNotEmpty() && repeatPassword.isNotEmpty() && newPassword == repeatPassword) {
                it.password = passwordEncoder.encode(newPassword)
            }
            it.enabled = enabled
            it.roles.clear()
            roleIds?.let { ids -> it.roles.addAll(roleRepo.findAllById(ids)) }
        }
        repo.save(it)
        return "redirect:/admin/users/${it.id}"
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
        return "redirect:/admin/users"
    }
}
