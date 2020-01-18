package ru.offenso.entities

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @CreatedBy
    @kotlinx.serialization.Transient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    lateinit var createdBy: User
    @field:CreatedDate
    @kotlinx.serialization.Transient
    lateinit var createdAt: LocalDateTime
    @LastModifiedBy
    @kotlinx.serialization.Transient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    lateinit var updatedBy: User
    @field:LastModifiedDate
    @kotlinx.serialization.Transient
    lateinit var updatedAt: LocalDateTime
}