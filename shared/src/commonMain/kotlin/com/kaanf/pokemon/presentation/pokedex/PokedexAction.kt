package com.kaanf.pokemon.presentation.pokedex

import com.kaanf.pokemon.domain.model.Pokemon

sealed interface PokedexAction {
    data object OnLoadMore : PokedexAction
    data object OnRetry : PokedexAction
    data class OnAddToTeam(val pokemon: Pokemon) : PokedexAction
}
