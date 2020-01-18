@file:UseSerializers(DateSerializer::class)

package ru.offenso.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.offenso.serializers.DateSerializer
import java.util.*
import javax.persistence.*

@Entity
@Serializable
@Table(name = "persistent_logins")
data class PersistentLogin(
    @Id
    var series: String = "",
    @ManyToOne
    @kotlinx.serialization.Transient
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,
    @Column(nullable = false)
    var token: String = "",
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    var lastUsed: Date = Date()
)