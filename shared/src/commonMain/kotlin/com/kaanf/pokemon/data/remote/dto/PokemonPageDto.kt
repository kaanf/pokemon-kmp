package com.kaanf.pokemon.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonPageDto(
    val count: Int,
    val results: List<NamedResourceDto>,
)

@Serializable
data class NamedResourceDto(
    val name: String,
    val url: String,
)
