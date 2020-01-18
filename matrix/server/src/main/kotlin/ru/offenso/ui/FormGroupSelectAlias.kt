package ru.offenso.ui

data class FormGroupSelectAlias(
    override val order: Int,
    val entity: String,
    val title: String,
    val placeholder: String,
    val name: String,
    val value: List<Long>,
    val multiple: Boolean?,
    val usingAlias: Boolean?,
    val alias: Any?,
    val help: String = "",
    val required: Boolean = false,
    val options: Map<Long, String>
) : FormGroup("form-group-select-alias", order)