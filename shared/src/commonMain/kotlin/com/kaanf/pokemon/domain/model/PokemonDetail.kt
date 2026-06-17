package com.kaanf.pokemon.domain.model


data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val shinyImageUrl: String,
    val heightDecimeters: Int,
    val weightHectograms: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val abilities: List<Ability>,
)
