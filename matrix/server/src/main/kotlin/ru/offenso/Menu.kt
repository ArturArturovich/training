package ru.offenso

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Menu(val title: String, val order: Int, vararg val authority: String)
