package ru.offenso.ui

data class FormGroupFile(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val type: String,
    val name: String,
    val value: List<String> = listOf(),
    val help: String = "",
    val required: Boolean = false
) : FormGroup("form-group-file", order)
