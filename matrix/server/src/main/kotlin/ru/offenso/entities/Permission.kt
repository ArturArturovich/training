@file: UseSerializers(DateSerializer::class)

package ru.offenso.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.offenso.serializers.DateSerializer
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Serializable
@Table(name = "permissions")
data class Permission(
    @Column(length = 255, unique = true, nullable = false)
    var name: String = "",
    var description: String = ""
) : AuditEntity()