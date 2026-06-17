package com.kaanf.pokemon.presentation.mapper

import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.UnitSystem
import com.kaanf.pokemon.presentation.detail.model.DetailUiModel
import com.kaanf.pokemon.presentation.util.formatHeight
import com.kaanf.pokemon.presentation.util.formatWeight

fun PokemonDetail.toUi(unitSystem: UnitSystem): DetailUiModel = DetailUiModel(
    id = id,
    name = name,
    imageUrl = imageUrl,
    shinyImageUrl = shinyImageUrl,
    height = formatHeight(heightDecimeters, unitSystem),
    weight = formatWeight(weightHectograms, unitSystem),
    types = types,
    stats = stats,
    abilities = abilities,
)
