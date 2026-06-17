package com.kaanf.pokemon.data.local.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kaanf.pokemon.core.database.PokemonDatabase
import com.kaanf.pokemon.data.local.entity.ObserveTeam
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import com.kaanf.pokemon.data.local.entity.TeamMemberEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SqlDelightTeamDao(
    private val database: PokemonDatabase,
) : TeamDao {
    private val teamQueries get() = database.teamMemberQueries
    private val pokemonQueries get() = database.pokemonQueries

    override fun observeTeam(): Flow<List<ObserveTeam>> {
        return teamQueries.observeTeam().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun addMember(
        pokemon: PokemonEntity,
        member: TeamMemberEntity,
    ) = withContext(Dispatchers.IO) {
        database.transaction {
            pokemonQueries.upsert(pokemon)
            teamQueries.insertMember(member)
        }
    }

    override suspend fun deleteMember(pokemonId: Int) {
        withContext(Dispatchers.IO) {
            teamQueries.deleteMember(pokemonId)
        }
    }
}
