package ru.offenso.models

import kotlinx.serialization.Serializable

@Serializable
data class ValuesFile(var ids: List<Long>? = null) : Values