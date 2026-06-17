package com.kaanf.pokemon.data.local.dao

import com.kaanf.pokemon.data.local.entity.ObserveTeam
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import com.kaanf.pokemon.data.local.entity.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

interface TeamDao {
    fun observeTeam(): Flow<List<ObserveTeam>>

    suspend fun addMember(pokemon: PokemonEntity, member: TeamMemberEntity)

    suspend fun deleteMember(pokemonId: Int)
}
