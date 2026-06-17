package com.kaanf.pokemon.core.database

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.kaanf.pokemon.data.local.abilityFieldsAdapter
import com.kaanf.pokemon.data.local.entity.PokemonDetailEntity
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import com.kaanf.pokemon.data.local.entity.TeamMemberEntity
import com.kaanf.pokemon.data.local.statFieldsAdapter
import com.kaanf.pokemon.data.local.stringListAdapter

fun createPokemonDatabase(driver: SqlDriver): PokemonDatabase {
    return PokemonDatabase(
        driver = driver,
        pokemonEntityAdapter = PokemonEntity.Adapter(
            idAdapter = IntColumnAdapter,
        ),
        pokemonDetailEntityAdapter = PokemonDetailEntity.Adapter(
            idAdapter = IntColumnAdapter,
            heightDecimetersAdapter = IntColumnAdapter,
            weightHectogramsAdapter = IntColumnAdapter,
            typesAdapter = stringListAdapter,
            statsAdapter = statFieldsAdapter,
            abilitiesAdapter = abilityFieldsAdapter,
        ),
        teamMemberEntityAdapter = TeamMemberEntity.Adapter(
            pokemonIdAdapter = IntColumnAdapter,
        ),
    )
}
