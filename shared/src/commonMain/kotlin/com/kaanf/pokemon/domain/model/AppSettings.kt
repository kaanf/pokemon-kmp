package com.kaanf.pokemon.domain.model

data class AppSettings(
    val unitSystem: UnitSystem = UnitSystem.METRIC,
    val timeFormat: TimeFormat = TimeFormat.TWENTY_FOUR_HOUR,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)

enum class UnitSystem {
    METRIC, IMPERIAL
}

enum class TimeFormat {
    TWENTY_FOUR_HOUR, TWELVE_HOUR
}

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}
