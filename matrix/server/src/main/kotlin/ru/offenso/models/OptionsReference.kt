package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class OptionsReference(
    override val help: String? = null,
    override val required: Boolean? = null,
    override val summary: Boolean? = null,
    val id: Long? = null,
    val multiple: Boolean? = null,
    val usingAlias: Boolean? = null,
    val childrenFields: List<Long>? = null,
    override val hideInTable: Boolean? = null,
    override val calculateTotal: Boolean? = null
) : Options