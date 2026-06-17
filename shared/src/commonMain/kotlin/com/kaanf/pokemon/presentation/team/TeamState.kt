package com.kaanf.pokemon.presentation.team

import com.kaanf.pokemon.domain.model.TeamMember
import com.kaanf.pokemon.domain.usecase.AddPokemonToTeamUseCase

data class TeamState(
    val members: List<TeamMember> = emptyList(),
    val maxTeamSize: Int = AddPokemonToTeamUseCase.MAX_TEAM_SIZE,
) {
    val emptySlots: Int get() = (maxTeamSize - members.size).coerceAtLeast(0)
}
