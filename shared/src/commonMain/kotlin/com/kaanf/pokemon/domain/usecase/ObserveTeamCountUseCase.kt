package com.kaanf.pokemon.domain.usecase

import com.kaanf.pokemon.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveTeamCountUseCase(
    private val teamRepository: TeamRepository,
) {
    operator fun invoke(): Flow<Int> =
        teamRepository.observeTeam().map { it.size }
}
