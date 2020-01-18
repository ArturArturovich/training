package ru.offenso.services

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.offenso.models.Principal
import ru.offenso.repositories.UserRepository

@Service
@Transactional
class UserDetailsService(var repo: UserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String) = Principal(repo.findByUsername(username).orElseThrow { UsernameNotFoundException("Username not found") })
}