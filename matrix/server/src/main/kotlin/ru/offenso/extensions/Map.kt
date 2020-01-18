package ru.offenso.extensions

inline fun <K, V> Map<K, V>.firstOrNull(predicate: (Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    for (element in this) if (predicate(element)) return element
    return null
}