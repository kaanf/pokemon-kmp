package com.kaanf.pokemon.domain.service

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.PokemonPage

interface PokeApiService {
    suspend fun getPokemonPage(limit: Int, offset: Int): Result<PokemonPage, DataError.Remote>
    suspend fun getPokemonDetail(id: Int): Result<PokemonDetail, DataError.Remote>
}
