package com.kaanf.pokemon.data.remote.mappers

import com.kaanf.pokemon.data.remote.dto.PokemonDetailDto
import com.kaanf.pokemon.data.remote.dto.PokemonPageDto
import com.kaanf.pokemon.domain.model.Ability
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.PokemonPage
import com.kaanf.pokemon.domain.model.PokemonStat
import com.kaanf.pokemon.domain.model.PokemonType
import com.kaanf.pokemon.domain.model.StatType

fun PokemonPageDto.toDomain(): PokemonPage {
    return PokemonPage(
        items = results.mapNotNull { resource ->
            val id = getIdFromUrl(resource.url) ?: return@mapNotNull null

            Pokemon(
                id = id,
                name = resource.name,
                imageUrl = getArtworkUrl(id),
            )
        },
        totalCount = count,
    )
}

fun PokemonDetailDto.toDomain(): PokemonDetail {
    return PokemonDetail(
        id = id,
        name = name,
        imageUrl = sprites.other?.officialArtwork?.frontDefault ?: getArtworkUrl(id),
        shinyImageUrl = sprites.other?.officialArtwork?.frontShiny ?: getShinyArtworkUrl(id),
        heightDecimeters = height,
        weightHectograms = weight,
        types = types
            .sortedBy { it.slot }
            .map { PokemonType.fromName(it.type.name) },
        stats = stats.map {
            PokemonStat(
                type = StatType.fromApiName(it.stat.name),
                value = it.baseStat,
            )
        },
        abilities = abilities.map {
            Ability(
                name = it.ability.name,
                isHidden = it.isHidden,
            )
        },
    )
}

fun getArtworkUrl(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

fun getShinyArtworkUrl(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/$id.png"
}

fun getIdFromUrl(url: String): Int? {
    return url.trimEnd('/').substringAfterLast('/').toIntOrNull()
}
