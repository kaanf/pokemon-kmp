package com.kaanf.pokemon.domain.repository

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.EmptyResult
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun observePokedex(): Flow<List<Pokemon>>
    fun observeDetail(id: Int): Flow<PokemonDetail?>
    suspend fun fetchNextPage(): Result<Boolean, DataError.Remote>
    suspend fun fetchDetail(id: Int): EmptyResult<DataError.Remote>
}
