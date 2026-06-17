package com.kaanf.pokemon.data.repository

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.EmptyResult
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.core.common.asEmptyResult
import com.kaanf.pokemon.core.common.map
import com.kaanf.pokemon.core.common.onSuccess
import com.kaanf.pokemon.data.local.dao.PokemonDao
import com.kaanf.pokemon.data.local.mappers.toDomain
import com.kaanf.pokemon.data.local.mappers.toEntity
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.repository.PokemonRepository
import com.kaanf.pokemon.domain.service.PokeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstPokemonRepository(
    private val pokeApiService: PokeApiService,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {
    override fun observePokedex(): Flow<List<Pokemon>> {
        return pokemonDao.observeAll()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun observeDetail(id: Int): Flow<PokemonDetail?> {
        return pokemonDao.observeDetail(id)
            .map { entity -> entity?.toDomain() }
    }

    override suspend fun fetchNextPage(): Result<Boolean, DataError.Remote> {
        val offset = pokemonDao.count()

        return pokeApiService
            .getPokemonPage(limit = PAGE_SIZE, offset = offset)
            .onSuccess { page ->
                pokemonDao.upsertAll(page.items.map { it.toEntity() })
            }
            .map { page -> offset + page.items.size >= page.totalCount }
    }

    override suspend fun fetchDetail(id: Int): EmptyResult<DataError.Remote> {
        return pokeApiService
            .getPokemonDetail(id)
            .onSuccess { detail ->
                pokemonDao.upsertDetail(detail.toEntity())
            }
            .asEmptyResult()
    }

    companion object {
        const val PAGE_SIZE = 24
    }
}
