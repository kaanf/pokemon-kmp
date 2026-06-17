package com.kaanf.pokemon.domain.model

data class TeamMember(
    val pokemon: Pokemon,
    val types: List<PokemonType>,
    val addedAtEpochMillis: Long,
)
