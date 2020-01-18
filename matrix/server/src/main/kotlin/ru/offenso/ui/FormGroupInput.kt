package ru.offenso.ui

data class FormGroupInput(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val type: String,
    val name: String,
    val value: Any?,
    val help: String = "",
    val required: Boolean = false
) : FormGroup("form-group-input", order)
