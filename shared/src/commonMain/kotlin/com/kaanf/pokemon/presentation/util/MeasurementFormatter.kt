package com.kaanf.pokemon.presentation.util

import com.kaanf.pokemon.domain.model.UnitSystem
import kotlin.math.abs
import kotlin.math.roundToInt

fun formatHeight(decimeters: Int, unitSystem: UnitSystem): String {
    return when (unitSystem) {
        UnitSystem.METRIC -> "${oneDecimal(decimeters / 10.0)} m"
        UnitSystem.IMPERIAL -> {
            val totalInches = (decimeters * INCHES_PER_DECIMETER).roundToInt()
            val feet = totalInches / 12
            val inches = totalInches % 12
            "$feet'${inches.toString().padStart(2, '0')}\""
        }
    }
}

fun formatWeight(hectograms: Int, unitSystem: UnitSystem): String {
    return when (unitSystem) {
        UnitSystem.METRIC -> "${oneDecimal(hectograms / 10.0)} kg"
        UnitSystem.IMPERIAL -> "${oneDecimal(hectograms * POUNDS_PER_HECTOGRAM)} lbs"
    }
}

private fun oneDecimal(value: Double): String {
    val scaled = (value * 10).roundToInt()
    return "${scaled / 10}.${abs(scaled % 10)}"
}

private const val INCHES_PER_DECIMETER = 3.937007874
private const val POUNDS_PER_HECTOGRAM = 0.22046226218
