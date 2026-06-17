package com.kaanf.pokemon.navigation

import kotlinx.serialization.Serializable

sealed interface PokemonGraph {
    @Serializable
    data object Home : PokemonGraph

    @Serializable
    data class Detail(val pokemonId: Int) : PokemonGraph
}
