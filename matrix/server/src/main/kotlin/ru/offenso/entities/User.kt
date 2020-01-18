@file: UseSerializers(DateSerializer::class)

package ru.offenso.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.offenso.serializers.DateSerializer
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Serializable
@Table(name = "users")
data class User(
    @Email
    @Column(nullable = false)
    var email: String = "",
    @Column(length = 255, unique = true, nullable = false)
    var username: String = "",
    @Column(length = 255, nullable = false)
    var password: String = "",
    var enabled: Boolean = false
) : AuditEntity() {
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: MutableSet<Role> = mutableSetOf()

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, optional = false)
    var profile: Profile? = null
}
