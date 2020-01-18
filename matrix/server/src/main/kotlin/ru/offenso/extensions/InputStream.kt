package ru.offenso.extensions

import java.io.File
import java.io.InputStream

fun InputStream.toFile(filePath: String) = use { File(filePath).also { it.parentFile.mkdirs() }.outputStream().use { copyTo(it) } }