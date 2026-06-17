package com.kaanf.pokemon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kaanf.pokemon.domain.model.AppSettings
import com.kaanf.pokemon.domain.model.ThemeMode
import com.kaanf.pokemon.domain.model.TimeFormat
import com.kaanf.pokemon.domain.model.UnitSystem
import com.kaanf.pokemon.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreSettingsRepository(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    override fun observeSettings(): Flow<AppSettings> {
        return dataStore.data
            .catch { cause ->
                if (cause is IOException) emit(emptyPreferences()) else throw cause
            }
            .map { preferences ->
                AppSettings(
                    unitSystem = preferences[KEY_UNIT_SYSTEM].toEnumOrDefault(UnitSystem.METRIC),
                    timeFormat = preferences[KEY_TIME_FORMAT].toEnumOrDefault(TimeFormat.TWENTY_FOUR_HOUR),
                    themeMode = preferences[KEY_THEME_MODE].toEnumOrDefault(ThemeMode.SYSTEM),
                )
            }
    }

    override suspend fun setUnitSystem(unitSystem: UnitSystem) {
        dataStore.edit { it[KEY_UNIT_SYSTEM] = unitSystem.name }
    }

    override suspend fun setTimeFormat(timeFormat: TimeFormat) {
        dataStore.edit { it[KEY_TIME_FORMAT] = timeFormat.name }
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { it[KEY_THEME_MODE] = themeMode.name }
    }

    private inline fun <reified T : Enum<T>> String?.toEnumOrDefault(default: T): T {
        return this?.let { name ->
            enumValues<T>().firstOrNull { it.name == name }
        } ?: default
    }

    private companion object {
        val KEY_UNIT_SYSTEM = stringPreferencesKey("unit_system")
        val KEY_TIME_FORMAT = stringPreferencesKey("time_format")
        val KEY_THEME_MODE = stringPreferencesKey("theme_mode")
    }
}
