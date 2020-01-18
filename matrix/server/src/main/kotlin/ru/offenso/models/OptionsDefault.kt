package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
open class OptionsDefault(
    override val help: String? = null,
    override val required: Boolean? = null,
    override val summary: Boolean? = null,
    override val hideInTable: Boolean? = null,
    override val calculateTotal: Boolean? = null
) : Options
