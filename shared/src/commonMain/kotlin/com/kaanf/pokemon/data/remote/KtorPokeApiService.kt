package com.kaanf.pokemon.data.remote

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.core.common.map
import com.kaanf.pokemon.core.network.get
import com.kaanf.pokemon.data.remote.dto.PokemonDetailDto
import com.kaanf.pokemon.data.remote.dto.PokemonPageDto
import com.kaanf.pokemon.data.remote.mappers.toDomain
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.PokemonPage
import com.kaanf.pokemon.domain.service.PokeApiService
import io.ktor.client.HttpClient

class KtorPokeApiService(
    private val httpClient: HttpClient,
) : PokeApiService {
    override suspend fun getPokemonPage(
        limit: Int,
        offset: Int
    ): Result<PokemonPage, DataError.Remote> {
        return httpClient.get<PokemonPageDto>(
            route = "/pokemon",
            queryParams = mapOf(
                "limit" to limit,
                "offset" to offset,
            )
        ).map { it.toDomain() }
    }

    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail, DataError.Remote> {
        return httpClient.get<PokemonDetailDto>(
            route = "/pokemon/$id"
        ).map { it.toDomain() }
    }
}
