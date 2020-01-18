package ru.offenso.ui

data class FormGroupTextarea(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val name: String,
    val value: Any?,
    val help: String = "",
    val rows: Int
) : FormGroup("form-group-textarea", order)