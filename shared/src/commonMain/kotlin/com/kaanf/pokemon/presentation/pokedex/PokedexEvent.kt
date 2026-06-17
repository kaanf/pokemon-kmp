package com.kaanf.pokemon.presentation.pokedex

import com.kaanf.pokemon.core.common.DataError

sealed interface PokedexEvent {
    data class ShowPagingError(val error: DataError.Remote) : PokedexEvent

    data object AddedToTeam : PokedexEvent

    data object RemovedFromTeam : PokedexEvent

    data object TeamFull : PokedexEvent

    data object AddToTeamFailed : PokedexEvent

    data object RemoveFromTeamFailed : PokedexEvent
}
