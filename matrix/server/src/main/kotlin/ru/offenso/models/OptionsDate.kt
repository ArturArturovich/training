package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
open class OptionsDate(
    override val help: String? = null,
    override val required: Boolean? = null,
    override val summary: Boolean? = null,
    val format: Int = 0,
    override val hideInTable: Boolean? = null,
    override val calculateTotal: Boolean? = null
) : Options