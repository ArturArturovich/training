package ru.offenso.ui

data class FormGroupSelect(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val name: String,
    val value: Any?,
    val help: String = "",
    val required: Boolean = false,
    val options: Map<String, String>
) : FormGroup("form-group-select", order)

