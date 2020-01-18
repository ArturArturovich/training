package ru.offenso

class MenuItem(
    val title: String,
    val url: String,
    val authority: Array<out String>,
    val order: Int,
    val children: MutableMap<String, MenuItem> = mutableMapOf()
)