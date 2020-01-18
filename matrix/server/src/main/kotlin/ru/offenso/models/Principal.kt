package ru.offenso.models

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import ru.offenso.entities.Role
import java.util.*

class Principal(
    val user: ru.offenso.entities.User,
    accountNonExpired: Boolean = true,
    credentialsNonExpired: Boolean = true,
    accountNonLocked: Boolean = true
) : User(user.username, user.password, user.enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(user.roles)),
    UserDetails {

    fun hasAuthority(permission: String) = authorities.any { it.authority == permission }

    companion object {
        fun getAuthorities(roles: Set<Role>) = TreeSet(SimpleGrantedAuthorityComparator()).also {
            for (role in roles) {
                it.addAll(getGrantedAuthorities(role))
            }
        }

        private fun getGrantedAuthorities(role: Role) = role.permissions.map { SimpleGrantedAuthority(it.name) }.toSet()

        private class SimpleGrantedAuthorityComparator : Comparator<SimpleGrantedAuthority> {
            override fun compare(o1: SimpleGrantedAuthority, o2: SimpleGrantedAuthority) = if (o1 == o2) 0 else -1
        }
    }
}