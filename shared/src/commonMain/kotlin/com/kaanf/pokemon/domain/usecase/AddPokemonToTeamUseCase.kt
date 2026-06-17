package com.kaanf.pokemon.domain.usecase

import com.kaanf.pokemon.core.common.EmptyResult
import com.kaanf.pokemon.core.common.Error
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.core.common.mapError
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.repository.TeamRepository
import kotlinx.coroutines.flow.first

class AddPokemonToTeamUseCase(
    private val teamRepository: TeamRepository,
) {

    suspend operator fun invoke(pokemon: Pokemon): EmptyResult<AddToTeamError> {
        val team = teamRepository.observeTeam().first()

        return when {
            team.any { it.pokemon.id == pokemon.id } -> Result.Failure(AddToTeamError.ALREADY_IN_TEAM)
            team.size >= MAX_TEAM_SIZE -> Result.Failure(AddToTeamError.TEAM_FULL)
            else -> teamRepository
                .addToTeam(pokemon)
                .mapError { AddToTeamError.STORAGE }
        }
    }

    companion object {
        const val MAX_TEAM_SIZE = 6
    }
}

enum class AddToTeamError : Error {
    TEAM_FULL,
    ALREADY_IN_TEAM,
    STORAGE
}
