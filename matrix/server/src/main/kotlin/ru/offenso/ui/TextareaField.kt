package ru.offenso.ui

@Target(AnnotationTarget.PROPERTY)
annotation class TextareaField(
    val order: Int,
    val title: String,
    val placeholder: String,
    val rows: Int = 5,
    val help: String = "")