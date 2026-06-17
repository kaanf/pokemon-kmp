package com.kaanf.pokemon.domain.repository

import com.kaanf.pokemon.domain.model.AppSettings
import com.kaanf.pokemon.domain.model.ThemeMode
import com.kaanf.pokemon.domain.model.TimeFormat
import com.kaanf.pokemon.domain.model.UnitSystem
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>

    suspend fun setUnitSystem(unitSystem: UnitSystem)
    suspend fun setTimeFormat(timeFormat: TimeFormat)
    suspend fun setThemeMode(themeMode: ThemeMode)
}
