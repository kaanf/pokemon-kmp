package com.kaanf.pokemon.presentation.model

sealed interface AppTopBarState {
    data object List : AppTopBarState
    data class Team(val teamCount: Int) : AppTopBarState
    data object Detail : AppTopBarState
}