package ru.offenso.ui

@Target(AnnotationTarget.PROPERTY)
annotation class InputField(
    val order: Int,
    val title: String,
    val placeholder: String,
    val help: String = "",
    val type: String = "text",
    val required: Boolean = false)
