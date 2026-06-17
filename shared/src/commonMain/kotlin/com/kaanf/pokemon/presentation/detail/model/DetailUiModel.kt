package com.kaanf.pokemon.presentation.detail.model

import com.kaanf.pokemon.domain.model.Ability
import com.kaanf.pokemon.domain.model.PokemonStat
import com.kaanf.pokemon.domain.model.PokemonType

data class DetailUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val shinyImageUrl: String,
    val height: String,
    val weight: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val abilities: List<Ability>,
)