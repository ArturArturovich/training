package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class OptionsTextarea(
    override val help: String? = null,
    override val required: Boolean? = null,
    override val summary: Boolean? = null,
    val row: Int? = null,
    override val hideInTable: Boolean? = null,
    override val calculateTotal: Boolean? = null
) : Options