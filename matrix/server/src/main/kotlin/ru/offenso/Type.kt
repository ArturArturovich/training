package ru.offenso

enum class Type(val formGroup: String) {
    TEXT("form-group-input"),
    TEXTAREA("form-group-textarea"),
    SELECT("form-group-select"),
    DATE("form-group-date"),
    RADIOS("form-group-input"),
    CHECKBOX("form-group-checkbox"),
    REFERENCE("form-group-select-alias"), // catalog reference (rename it later)
    FILE("form-group-file")
}
