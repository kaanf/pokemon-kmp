package com.kaanf.pokemon.presentation.util

fun String.toFormattedName(): String {
    return split('-', ' ')
        .filter { it.isNotBlank() }
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercaseChar() }
        }
}

fun formatPokedexNumber(id: Int): String {
    return "#${id.toString().padStart(3, '0')}"
}
