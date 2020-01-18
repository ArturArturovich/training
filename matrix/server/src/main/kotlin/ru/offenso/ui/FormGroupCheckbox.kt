package ru.offenso.ui

data class FormGroupCheckbox(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val name: String,
    val value: Any?,
    val help: String = "",
    val required: Boolean = false,
    val options: Array<String>
) : FormGroup("form-group-checkbox", order)