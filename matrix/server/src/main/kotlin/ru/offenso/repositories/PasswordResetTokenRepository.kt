package ru.offenso.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.transaction.annotation.Transactional
import ru.offenso.entities.PasswordResetToken
import java.time.LocalDateTime
import java.util.*

interface PasswordResetTokenRepository : PagingAndSortingRepository<PasswordResetToken, Long> {
    fun findByIdAndTokenAndExpireAtAfter(id: Long, token: String, expireAt: LocalDateTime = LocalDateTime.now()): Optional<PasswordResetToken>

    @Transactional
    fun deleteByIdAndToken(id: Long, token: String)
}