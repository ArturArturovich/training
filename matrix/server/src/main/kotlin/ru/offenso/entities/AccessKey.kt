@file: UseSerializers(DateSerializer::class)

package ru.offenso.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.offenso.serializers.DateSerializer
import javax.persistence.*

@Entity
@Serializable
@Table(name = "access_keys")
data class AccessKey(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @kotlinx.serialization.Transient
    var user: User? = null,
    @Column(name = "`key`")
    var key: String
) : AuditEntity()