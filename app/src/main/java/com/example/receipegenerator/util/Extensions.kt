package com.example.receipegenerator.util

fun String.extractJsonArray(): String? {
    val start = indexOf('[')
    val end = lastIndexOf(']')
    return if (start != -1 && end != -1 && end > start) {
        substring(start, end + 1)
    } else {
        null
    }
}