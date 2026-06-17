package com.kaanf.pokemon.presentation.settings

import com.kaanf.pokemon.domain.model.ThemeMode
import com.kaanf.pokemon.domain.model.TimeFormat
import com.kaanf.pokemon.domain.model.UnitSystem

sealed interface SettingsAction {
    data class OnUnitSystemSelect(val unitSystem: UnitSystem) : SettingsAction
    data class OnTimeFormatSelect(val timeFormat: TimeFormat) : SettingsAction
    data class OnThemeModeSelect(val themeMode: ThemeMode) : SettingsAction
}
