package ru.offenso.extensions

fun <T> List<T>?.orEmpty() = this ?: listOf()
