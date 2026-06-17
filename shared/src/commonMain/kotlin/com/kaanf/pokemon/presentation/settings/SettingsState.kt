package com.kaanf.pokemon.presentation.settings

import com.kaanf.pokemon.domain.model.AppSettings

data class SettingsState(
    val settings: AppSettings = AppSettings(),
)
