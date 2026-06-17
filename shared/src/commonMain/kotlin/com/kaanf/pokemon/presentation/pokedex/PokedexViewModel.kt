package com.kaanf.pokemon.presentation.pokedex

import com.kaanf.pokemon.core.common.onFailure
import com.kaanf.pokemon.core.common.onSuccess
import com.kaanf.pokemon.core.network.ConnectivityObserver
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.repository.PokemonRepository
import com.kaanf.pokemon.domain.repository.TeamRepository
import com.kaanf.pokemon.domain.usecase.AddPokemonToTeamUseCase
import com.kaanf.pokemon.domain.usecase.AddToTeamError
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class PokedexViewModel(
    private val pokemonRepository: PokemonRepository,
    private val connectivityObserver: ConnectivityObserver,
    private val addPokemonToTeam: AddPokemonToTeamUseCase,
    private val teamRepository: TeamRepository,
) : ViewModel() {
    private val eventChannel = Channel<PokedexEvent>()

    @NativeCoroutines
    val events: Flow<PokedexEvent>
        get() = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private var pagingErrorActive = false

    private var loadMorePending = false

    private val _state = MutableStateFlow(PokedexState())

    @NativeCoroutinesState
    val state: StateFlow<PokedexState> = combine(
        _state,
        pokemonRepository.observePokedex(),
        connectivityObserver.isConnected,
        teamRepository.observeTeam(),
    ) { currentState, pokemon, isConnected, team ->
        currentState.copy(
            pokemon = pokemon,
            isOffline = !isConnected,
            teamMemberIds = team.mapTo(mutableSetOf()) { it.pokemon.id },
        )
    }
        .onStart {
            if (!hasLoadedInitialData) {
                hasLoadedInitialData = true
                loadNextPage()
                retryWhenConnectionRestored()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            PokedexState(),
        )

    fun onAction(action: PokedexAction) {
        when (action) {
            PokedexAction.OnLoadMore -> loadNextPage()
            PokedexAction.OnRetry -> {
                pagingErrorActive = false
                loadNextPage()
            }
            is PokedexAction.OnAddToTeam -> onAddToTeamClick(action.pokemon)
        }
    }

    private fun onAddToTeamClick(pokemon: Pokemon) {
        if (pokemon.id in state.value.teamMemberIds) {
            removeFromTeam(pokemon.id)
        } else {
            addToTeam(pokemon)
        }
    }

    private fun addToTeam(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonRepository.fetchDetail(pokemon.id)

            addPokemonToTeam(pokemon)
                .onSuccess { eventChannel.send(PokedexEvent.AddedToTeam) }
                .onFailure { error ->
                    when (error) {
                        AddToTeamError.TEAM_FULL -> eventChannel.send(PokedexEvent.TeamFull)
                        AddToTeamError.ALREADY_IN_TEAM -> Unit
                        AddToTeamError.STORAGE -> eventChannel.send(PokedexEvent.AddToTeamFailed)
                    }
                }
        }
    }

    private fun removeFromTeam(pokemonId: Int) {
        viewModelScope.launch {
            teamRepository.removeFromTeam(pokemonId)
                .onSuccess { eventChannel.send(PokedexEvent.RemovedFromTeam) }
                .onFailure { eventChannel.send(PokedexEvent.RemoveFromTeamFailed) }
        }
    }

    private fun loadNextPage() {
        val current = _state.value
        if (current.endReached) return
        if (current.isInitialLoading || current.isLoadingMore) {
            loadMorePending = true
            return
        }

        viewModelScope.launch {
            val hasCachedContent = pokemonRepository.observePokedex().first().isNotEmpty()
            _state.update {
                it.copy(
                    isInitialLoading = !hasCachedContent,
                    isLoadingMore = hasCachedContent,
                    error = null,
                )
            }

            val result = pokemonRepository.fetchNextPage()

            if (!hasCachedContent) {
                delay(INITIAL_LOADING_VISIBLE)
            }

            result
                .onSuccess { endReached ->
                    pagingErrorActive = false
                    _state.update {
                        it.copy(
                            isInitialLoading = false,
                            isLoadingMore = false,
                            endReached = endReached,
                        )
                    }
                    val shouldCatchUp = loadMorePending && !endReached
                    loadMorePending = false
                    if (shouldCatchUp) loadNextPage()
                }
                .onFailure { error ->
                    loadMorePending = false
                    _state.update {
                        it.copy(
                            isInitialLoading = false,
                            isLoadingMore = false,
                            error = if (hasCachedContent) null else error,
                        )
                    }

                    if (hasCachedContent && !pagingErrorActive) {
                        pagingErrorActive = true
                        eventChannel.send(PokedexEvent.ShowPagingError(error))
                    }
                }
        }
    }

    private fun retryWhenConnectionRestored() {
        viewModelScope.launch {
            connectivityObserver.isConnected
                .distinctUntilChanged()
                .filter { it }
                .collect {
                    pagingErrorActive = false
                    if (state.value.error != null) {
                        loadNextPage()
                    }
                }
        }
    }

    companion object {
        private val INITIAL_LOADING_VISIBLE = 1_500.milliseconds
    }
}
