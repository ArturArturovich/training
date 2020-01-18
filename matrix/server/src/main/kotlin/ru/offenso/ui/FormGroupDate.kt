package ru.offenso.ui

data class FormGroupDate(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val name: String,
    val value: Long,
    val help: String = "",
    val required: Boolean = false,
    val format: String?
) : FormGroup("form-group-date", order)