package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class ValuesReference(
    var ids: List<Long> = listOf(),
    var alias: String? = ""
): Values