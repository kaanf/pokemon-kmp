package com.kaanf.pokemon.data.local.dao

import com.kaanf.pokemon.data.local.entity.PokemonDetailEntity
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonDao {
    fun observeAll(): Flow<List<PokemonEntity>>

    suspend fun count(): Int

    suspend fun upsertAll(pokemon: List<PokemonEntity>)

    fun observeDetail(id: Int): Flow<PokemonDetailEntity?>

    suspend fun upsertDetail(detail: PokemonDetailEntity)
}
