package com.kaanf.pokemon.testutil

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.EmptyResult
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.core.network.ConnectivityObserver
import com.kaanf.pokemon.data.local.dao.PokemonDao
import com.kaanf.pokemon.data.local.entity.PokemonDetailEntity
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.PokemonPage
import com.kaanf.pokemon.domain.model.TeamMember
import com.kaanf.pokemon.domain.repository.PokemonRepository
import com.kaanf.pokemon.domain.repository.TeamRepository
import com.kaanf.pokemon.domain.service.PokeApiService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeTeamRepository : TeamRepository {
    val team = MutableStateFlow<List<TeamMember>>(emptyList())
    var addResult: EmptyResult<DataError.Local> = Result.Success(Unit)

    override fun observeTeam(): Flow<List<TeamMember>> = team

    override suspend fun addToTeam(pokemon: Pokemon): EmptyResult<DataError.Local> {
        if (addResult is Result.Success) {
            team.update { it + TeamMember(pokemon = pokemon, types = emptyList(), addedAtEpochMillis = 0L) }
        }
        return addResult
    }

    override suspend fun removeFromTeam(pokemonId: Int): EmptyResult<DataError.Local> {
        team.update { members -> members.filterNot { it.pokemon.id == pokemonId } }
        return Result.Success(Unit)
    }
}

class FakePokemonRepository : PokemonRepository {
    val pokedex = MutableStateFlow<List<Pokemon>>(emptyList())
    val detail = MutableStateFlow<PokemonDetail?>(null)

    var nextPageResult: Result<Boolean, DataError.Remote> = Result.Success(false)
    var pageToEmit: List<Pokemon> = emptyList()
    var fetchNextPageCount = 0

    var fetchNextPageGate: CompletableDeferred<Unit>? = null

    var fetchDetailResult: EmptyResult<DataError.Remote> = Result.Success(Unit)

    override fun observePokedex(): Flow<List<Pokemon>> = pokedex

    override fun observeDetail(id: Int): Flow<PokemonDetail?> = detail

    override suspend fun fetchNextPage(): Result<Boolean, DataError.Remote> {
        fetchNextPageCount++
        fetchNextPageGate?.await()
        if (nextPageResult is Result.Success) {
            pokedex.update { it + pageToEmit }
        }
        return nextPageResult
    }

    override suspend fun fetchDetail(id: Int): EmptyResult<DataError.Remote> = fetchDetailResult
}

class FakePokeApiService : PokeApiService {
    var pageResult: Result<PokemonPage, DataError.Remote> = Result.Success(PokemonPage(emptyList(), 0))
    var detailResult: Result<PokemonDetail, DataError.Remote> =
        Result.Failure(DataError.Remote.UNKNOWN)

    var lastLimit: Int? = null
    var lastOffset: Int? = null

    override suspend fun getPokemonPage(limit: Int, offset: Int): Result<PokemonPage, DataError.Remote> {
        lastLimit = limit
        lastOffset = offset
        return pageResult
    }

    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail, DataError.Remote> = detailResult
}

class FakePokemonDao : PokemonDao {
    val pokemon = MutableStateFlow<List<PokemonEntity>>(emptyList())
    val details = MutableStateFlow<Map<Int, PokemonDetailEntity>>(emptyMap())

    var upsertedPokemon: List<PokemonEntity>? = null
    var upsertDetailCount = 0

    override fun observeAll(): Flow<List<PokemonEntity>> = pokemon

    override suspend fun count(): Int = pokemon.value.size

    override suspend fun upsertAll(pokemon: List<PokemonEntity>) {
        upsertedPokemon = pokemon
        this.pokemon.update { existing ->
            (existing + pokemon).associateBy { it.id }.values.toList()
        }
    }

    override fun observeDetail(id: Int): Flow<PokemonDetailEntity?> =
        details.map { it[id] }

    override suspend fun upsertDetail(detail: PokemonDetailEntity) {
        upsertDetailCount++
        details.update { it + (detail.id to detail) }
    }
}

class FakeConnectivityObserver(
    initiallyConnected: Boolean = true,
) : ConnectivityObserver {
    val connected = MutableStateFlow(initiallyConnected)
    override val isConnected: Flow<Boolean> = connected
}
