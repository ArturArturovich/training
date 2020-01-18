package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class OptionsSelect(
    override val help: String? = null,
    override val required: Boolean? = null,
    override val summary: Boolean? = null,
    val options: Map<String, String>? = null,
    override val hideInTable: Boolean? = null,
    override val calculateTotal: Boolean? = null
) : Options