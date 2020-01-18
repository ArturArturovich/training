package ru.offenso.entities

import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Serializable
@Table(name = "roles")
data class Role(
    @Column(length = 255, unique = true, nullable = false)
    var name: String = "",
    var displayName: String = "",
    var description: String = ""
) : AuditEntity() {
    @kotlinx.serialization.Transient
    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = [JoinColumn(name = "role_id")], inverseJoinColumns = [JoinColumn(name = "permission_id")])
    var permissions: MutableSet<Permission> = mutableSetOf()
}