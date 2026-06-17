package com.kaanf.pokemon.data.local.mappers

import com.kaanf.pokemon.data.local.entity.AbilityField
import com.kaanf.pokemon.data.local.entity.ObserveTeam
import com.kaanf.pokemon.data.local.entity.PokemonDetailEntity
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import com.kaanf.pokemon.data.local.entity.StatField
import com.kaanf.pokemon.data.remote.mappers.getShinyArtworkUrl
import com.kaanf.pokemon.domain.model.Ability
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.PokemonStat
import com.kaanf.pokemon.domain.model.PokemonType
import com.kaanf.pokemon.domain.model.StatType
import com.kaanf.pokemon.domain.model.TeamMember

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}

fun ObserveTeam.toDomain(): TeamMember {
    return TeamMember(
        pokemon = Pokemon(id = id, name = name, imageUrl = imageUrl),
        types = types.orEmpty().map { PokemonType.fromName(it) },
        addedAtEpochMillis = addedAtEpochMillis,
    )
}

fun PokemonDetailEntity.toDomain(): PokemonDetail {
    return PokemonDetail(
        id = id,
        name = name,
        imageUrl = imageUrl,
        shinyImageUrl = getShinyArtworkUrl(id),
        heightDecimeters = heightDecimeters,
        weightHectograms = weightHectograms,
        types = types.map { PokemonType.fromName(it) },
        stats = stats.map { PokemonStat(type = StatType.fromApiName(it.name), value = it.value) },
        abilities = abilities.map { Ability(name = it.name, isHidden = it.isHidden) },
    )
}

fun PokemonDetail.toEntity(): PokemonDetailEntity {
    return PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        heightDecimeters = heightDecimeters,
        weightHectograms = weightHectograms,
        types = types.map { it.name.lowercase() },
        stats = stats.map { StatField(name = it.type.apiName, value = it.value) },
        abilities = abilities.map { AbilityField(name = it.name, isHidden = it.isHidden) },
    )
}
