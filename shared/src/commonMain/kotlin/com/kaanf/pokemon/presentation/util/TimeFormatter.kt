package com.kaanf.pokemon.presentation.util

import com.kaanf.pokemon.domain.model.TimeFormat

fun formatTime(hour: Int, minute: Int, timeFormat: TimeFormat): String {
    require(hour in 0..23) { "hour must be in 0..23, was $hour" }
    require(minute in 0..59) { "minute must be in 0..59, was $minute" }

    val paddedMinute = minute.toString().padStart(2, '0')

    return when (timeFormat) {
        TimeFormat.TWENTY_FOUR_HOUR -> "${hour.toString().padStart(2, '0')}:$paddedMinute"
        TimeFormat.TWELVE_HOUR -> {
            val amPm = if (hour < 12) "AM" else "PM"
            val hour12 = (hour % 12).let { if (it == 0) 12 else it }
            "$hour12:$paddedMinute $amPm"
        }
    }
}
