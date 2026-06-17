package com.kaanf.pokemon.presentation.team

import com.kaanf.pokemon.core.common.onFailure
import com.kaanf.pokemon.core.common.onSuccess
import com.kaanf.pokemon.domain.repository.TeamRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow

class TeamViewModel(
    private val teamRepository: TeamRepository,
) : ViewModel() {

    private val eventChannel = Channel<TeamEvent>()

    @NativeCoroutines
    val events: Flow<TeamEvent>
        get() = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(TeamState())
    @NativeCoroutinesState
    val state: StateFlow<TeamState> = combine(
        _state,
        teamRepository.observeTeam(),
    ) { currentState, members ->
        currentState.copy(members = members)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            TeamState(),
        )

    fun onAction(action: TeamAction) {
        when (action) {
            is TeamAction.OnRemoveMember -> {
                viewModelScope.launch {
                    teamRepository.removeFromTeam(action.pokemonId)
                        .onSuccess { eventChannel.send(TeamEvent.RemovedFromTeam) }
                        .onFailure { eventChannel.send(TeamEvent.RemoveFailed) }
                }
            }
        }
    }
}
