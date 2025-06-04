package com.example.rutifyclient.utils

fun obtenerEmojiEstado(codigo: String): String {
    return when (codigo) {
        "F" -> "😄"
        "M" -> "💪"
        "D" -> "🤔"
        "C" -> "😩"
        "L" -> "😴"
        "R" -> "🧘"
        "E" -> "🏋️"
        else -> "❓"
    }
}