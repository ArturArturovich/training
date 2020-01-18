package ru.offenso.extensions

import java.io.File

fun File.deleteIfExists() {
    if (exists()) delete()
}