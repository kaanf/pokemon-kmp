package com.kaanf.pokemon.data.repository

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.EmptyResult
import com.kaanf.pokemon.core.database.safeDatabaseUpdate
import com.kaanf.pokemon.data.local.dao.TeamDao
import com.kaanf.pokemon.data.local.entity.TeamMemberEntity
import com.kaanf.pokemon.data.local.mappers.toDomain
import com.kaanf.pokemon.data.local.mappers.toEntity
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.TeamMember
import com.kaanf.pokemon.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SqlDelightTeamRepository(
    private val teamDao: TeamDao,
) : TeamRepository {

    override fun observeTeam(): Flow<List<TeamMember>> {
        return teamDao.observeTeam()
            .map { rows -> rows.map { it.toDomain() } }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun addToTeam(pokemon: Pokemon): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            teamDao.addMember(
                pokemon = pokemon.toEntity(),
                member = TeamMemberEntity(
                    pokemonId = pokemon.id,
                    addedAtEpochMillis = Clock.System.now().toEpochMilliseconds(),
                ),
            )
        }
    }

    override suspend fun removeFromTeam(pokemonId: Int): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            teamDao.deleteMember(pokemonId)
        }
    }
}
