package ru.offenso.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.offenso.entities.PasswordResetToken
import ru.offenso.exceptions.BadRequestException
import ru.offenso.exceptions.ForbiddenException
import ru.offenso.exceptions.NotFoundException
import ru.offenso.extensions.orElseNull
import ru.offenso.models.Principal
import ru.offenso.repositories.PasswordResetTokenRepository
import ru.offenso.repositories.UserRepository
import ru.offenso.services.EmailService
import java.time.LocalDateTime
import java.util.*

@Controller
@RequestMapping("/forgot")
class ForgotController(
    private val repo: PasswordResetTokenRepository,
    private val userRepo: UserRepository,
    private val emailService: EmailService,
    private val passwordEncoder: PasswordEncoder
) : BaseController() {
    @GetMapping
    fun get(model: Model): String {
        model.addAttribute("email", "")
        model.addAttribute("error", "")
        return "forgot"
    }

    @PostMapping
    fun post(
        @RequestParam(name = "email") email: String,
        model: Model
    ) = with(userRepo.findByEmail(email)) {
        if (isPresent) {
            val user = get()
            val tokenModel = repo.save(repo.findById(user.id!!).orElseGet { PasswordResetToken(user) }.apply {
                token = UUID.randomUUID().toString()
                expireAt = LocalDateTime.now().plusHours(PasswordResetToken.EXPIRE_AT_HOURS)
            })
            emailService.send(user.email, "", EmailService.MailTemplate.FORGOT, mapOf("link" to "/forgot/${user.id}/${tokenModel.token}"))
//            "redirect:/forgot/${user.id}/${tokenModel.token}"
            "redirect:/login"
        } else {
            model.addAttribute("email", email)
            model.addAttribute("error", "user not found")
            "forgot"
        }
    }

    @GetMapping("/{id}/{token}")
    fun get(
        @PathVariable("id") id: Long,
        @PathVariable("token") token: String,
        model: Model
    ): String {
        val passwordResetToken = repo.findByIdAndTokenAndExpireAtAfter(id, token).orElseNull()
        repo.deleteByIdAndToken(id, token)
        val user = passwordResetToken?.user ?: throw NotFoundException("token not found")
        if (!user.enabled) throw ForbiddenException("user is blocked")
        val principal = Principal(user)
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(principal, null, listOf(SimpleGrantedAuthority("change password")))
        return "changePassword"
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAuthority('change password')")
    fun changePassword(
        @RequestParam("newPassword") newPassword: String,
        @RequestParam("confirmPassword") confirmPassword: String,
        @AuthenticationPrincipal principal: Principal
    ): String {
        (if (newPassword != confirmPassword) throw BadRequestException("New password don't equal Confirm password")
        else userRepo.updateByUsername(principal.user.username, passwordEncoder.encode(newPassword)))
            .also { SecurityContextHolder.getContext().authentication = null }
        return "redirect:/login" // TODO: ? Пароль успешно сменен ?
    }

}
