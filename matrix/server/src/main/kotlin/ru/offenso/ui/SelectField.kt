package ru.offenso.ui

@Target(AnnotationTarget.PROPERTY)
annotation class SelectField(
    val order: Int,
    val title: String,
    val placeholder: String,
    val help: String = "",
    val options: Array<String> = [],
    val required: Boolean = false
)