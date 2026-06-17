package com.kaanf.pokemon.presentation.detail

import com.kaanf.pokemon.core.common.onFailure
import com.kaanf.pokemon.domain.repository.PokemonRepository
import com.kaanf.pokemon.domain.repository.SettingsRepository
import com.kaanf.pokemon.presentation.mapper.toUi
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class DetailViewModel(
    private val pokemonId: Int,
    private val pokemonRepository: PokemonRepository,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(DetailState())

    @NativeCoroutinesState
    val state: StateFlow<DetailState> = combine(
        _state,
        pokemonRepository.observeDetail(pokemonId),
        settingsRepository.observeSettings(),
    ) { currentState, detail, settings ->
        currentState.copy(
            detail = detail?.toUi(settings.unitSystem),
        )
    }
        .onStart {
            if (!hasLoadedInitialData) {
                hasLoadedInitialData = true
                fetchDetail()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            DetailState(),
        )

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.OnRetry -> fetchDetail()
        }
    }

    private fun fetchDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            pokemonRepository
                .fetchDetail(pokemonId)
                .onFailure { error ->
                    _state.update { it.copy(error = error) }
                }

            _state.update { it.copy(isLoading = false) }
        }
    }
}
