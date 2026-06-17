package com.kaanf.pokemon.presentation.team

sealed interface TeamAction {
    data class OnRemoveMember(val pokemonId: Int) : TeamAction
}
