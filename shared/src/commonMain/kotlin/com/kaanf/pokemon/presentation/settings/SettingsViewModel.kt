package com.kaanf.pokemon.presentation.settings

import com.kaanf.pokemon.domain.repository.SettingsRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    @NativeCoroutinesState
    val state: StateFlow<SettingsState> = settingsRepository
        .observeSettings()
        .map { SettingsState(settings = it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            SettingsState(),
        )

    fun onAction(action: SettingsAction) {
        viewModelScope.launch {
            when (action) {
                is SettingsAction.OnUnitSystemSelect -> {
                    settingsRepository.setUnitSystem(action.unitSystem)
                }
                is SettingsAction.OnTimeFormatSelect -> {
                    settingsRepository.setTimeFormat(action.timeFormat)
                }
                is SettingsAction.OnThemeModeSelect -> {
                    settingsRepository.setThemeMode(action.themeMode)
                }
            }
        }
    }
}
