package ru.offenso.extensions

import java.util.*

fun <T> Optional<T>.orElseNull(): T? = orElseGet { null }