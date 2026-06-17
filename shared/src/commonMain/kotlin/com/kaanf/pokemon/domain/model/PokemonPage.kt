package com.kaanf.pokemon.domain.model

data class PokemonPage(
    val items: List<Pokemon>,
    val totalCount: Int,
)
