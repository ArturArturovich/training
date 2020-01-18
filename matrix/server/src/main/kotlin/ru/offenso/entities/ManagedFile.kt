package ru.offenso.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "managed_files")
data class ManagedFile(
    var entity: String,
    @Column(name = "entity_id")
    var entityId: Long,
    @Column(name = "field_id")
    var fieldId: Long,
    var fileName: String = ""
) : AuditEntity()