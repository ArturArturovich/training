package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class ValuesDefault(
    var value: String? = null
) : Values