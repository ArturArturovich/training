package ru.offenso.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.offenso.entities.Profile
import ru.offenso.exceptions.NotFoundException
import ru.offenso.models.Principal
import ru.offenso.repositories.UserRepository

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('own profile')")
class UserController(
    private val passwordEncoder: PasswordEncoder,
    private val repo: UserRepository
) : BaseController() {
    companion object {
        const val PATH = "user"
    }

    @GetMapping("", "/")
    fun index(
        @AuthenticationPrincipal principal: Principal,
        model: Model
    ): String {
        model.addAttribute("user", repo.findById(principal.user.id!!).get())
        model.addAttribute("url", "/user")
        return "$PATH/index"
    }

    @PostMapping("", "/")
    fun indexPost(
        @RequestParam(name = "profile.secret") secret: String,
        @AuthenticationPrincipal principal: Principal
    ): String {
        val user = repo.save(repo.findById(principal.user.id!!).orElseThrow { NotFoundException("User not found") }.apply {
            profile = profile ?: Profile(this)
            profile!!.secret = secret
        })
        principal.user.profile = user.profile
        return "redirect:/user"
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('administer users')")
    fun user(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        model.addAttribute("user", repo.findById(id).orElseThrow { NotFoundException() })
        model.addAttribute("url", "/user/$id")
        return "$PATH/index"
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('administer users')")
    fun userPost(
        @PathVariable("id") id: Long,
        @RequestParam(name = "profile.secret") secret: String
    ): String {
        repo.save(repo.findById(id).orElseThrow { NotFoundException() }.apply {
            profile = profile ?: Profile(this)
            profile!!.secret = secret
        })
        return "redirect:/user/$id"
    }

    @PostMapping("/password")
    fun changePassword(
        @AuthenticationPrincipal principal: Principal,
        model: Model,
        @RequestParam("old-password") oldPassword: String,
        @RequestParam("new-password") newPassword: String,
        @RequestParam("new-password-repeat") newPasswordRepeat: String
    ) = repo.findById(principal.user.id!!).orElseThrow { NotFoundException() }.let {
        model.addAttribute("user", it)
        model.addAttribute("url", "/user")
        when {
            !passwordEncoder.matches(oldPassword, it.password) -> {
                model.addAttribute("error", "old")
                "$PATH/index"
            }
            newPassword != newPasswordRepeat -> {
                model.addAttribute("error", "new")
                "$PATH/index"
            }
            else -> {
                it.password = passwordEncoder.encode(newPassword)
                repo.save(it)
                "redirect:/user"
            }
        }
    }
}
