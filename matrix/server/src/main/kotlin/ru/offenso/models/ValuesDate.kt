package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class ValuesDate(
    var value: Long? = null
) : Values