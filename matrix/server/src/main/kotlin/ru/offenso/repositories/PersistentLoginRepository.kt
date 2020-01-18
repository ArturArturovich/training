package ru.offenso.repositories

import org.springframework.data.repository.CrudRepository
import ru.offenso.entities.PersistentLogin
import ru.offenso.entities.User

interface PersistentLoginRepository : CrudRepository<PersistentLogin, String> {
    fun deleteAllByUser(user: User)
}
