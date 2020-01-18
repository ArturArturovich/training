package ru.offenso

import kotlinx.serialization.Serializable

@Serializable
data class PutNodeValues(val id: Long, val fields: List<Field>) {
    @Serializable
    data class Field(var id: Long, var value: String)
}