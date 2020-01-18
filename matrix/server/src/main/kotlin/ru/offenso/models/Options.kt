package ru.offenso.models

interface Options {
    val help: String?
    val required: Boolean?
    val summary: Boolean?
    val hideInTable: Boolean?
    val calculateTotal: Boolean?
}