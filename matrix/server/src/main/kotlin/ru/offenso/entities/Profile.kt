@file: UseSerializers(DateSerializer::class)

package ru.offenso.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.offenso.serializers.DateSerializer
import javax.persistence.*

@Entity
@Serializable
@Table(name = "profiles")
data class Profile(
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    var user: User? = null,
    @Column(length = 255, unique = true, nullable = false)
    var secret: String = ""
) : AuditEntity()