package ru.offenso.repositories

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import ru.offenso.entities.User
import java.util.*

interface UserRepository : CommonRepository<User> {

    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.username = :username")
    fun updateByUsername(username: String, password: String)
}
