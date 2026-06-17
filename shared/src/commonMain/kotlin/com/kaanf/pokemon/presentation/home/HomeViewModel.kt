package com.kaanf.pokemon.presentation.home

import com.kaanf.pokemon.domain.usecase.ObserveTeamCountUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    observeTeamCount: ObserveTeamCountUseCase,
) : ViewModel() {
    @NativeCoroutinesState
    val teamCount: StateFlow<Int> = observeTeamCount()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            0,
        )
}
