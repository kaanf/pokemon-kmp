package com.kaanf.pokemon.presentation.pokedex

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.domain.model.Pokemon

data class PokedexState(
    val pokemon: List<Pokemon>? = null,
    val teamMemberIds: Set<Int> = emptySet(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val endReached: Boolean = false,
    val error: DataError.Remote? = null,
    val isOffline: Boolean = false,
)
