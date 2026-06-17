package com.kaanf.pokemon.data.local.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.kaanf.pokemon.core.database.PokemonDatabase
import com.kaanf.pokemon.data.local.entity.PokemonDetailEntity
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SqlDelightPokemonDao(
    private val database: PokemonDatabase,
) : PokemonDao {
    private val pokemonQueries get() = database.pokemonQueries
    private val detailQueries get() = database.pokemonDetailQueries

    override fun observeAll(): Flow<List<PokemonEntity>> {
        return pokemonQueries.observeAll().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun count(): Int = withContext(Dispatchers.IO) {
        pokemonQueries.count().executeAsOne().toInt()
    }

    override suspend fun upsertAll(pokemon: List<PokemonEntity>) = withContext(Dispatchers.IO) {
        pokemonQueries.transaction {
            pokemon.forEach(pokemonQueries::upsert)
        }
    }

    override fun observeDetail(id: Int): Flow<PokemonDetailEntity?> {
        return detailQueries.observeDetail(id).asFlow().mapToOneOrNull(Dispatchers.IO)
    }

    override suspend fun upsertDetail(detail: PokemonDetailEntity) {
        withContext(Dispatchers.IO) {
            detailQueries.upsertDetail(detail)
        }
    }
}
