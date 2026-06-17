package com.kaanf.pokemon.domain.repository

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.EmptyResult
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.TeamMember
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun observeTeam(): Flow<List<TeamMember>>

    suspend fun addToTeam(pokemon: Pokemon): EmptyResult<DataError.Local>
    suspend fun removeFromTeam(pokemonId: Int): EmptyResult<DataError.Local>
}
