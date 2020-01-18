package ru.offenso.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "password_reset_tokens", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "token"])])
data class PasswordResetToken(
    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    var user: User? = null,
    var token: String = "",
    var expireAt: LocalDateTime = LocalDateTime.now().plusHours(EXPIRE_AT_HOURS)
) {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        const val EXPIRE_AT_HOURS = 24L
    }
}