package com.kaanf.pokemon.presentation.team

sealed interface TeamEvent {
    data object RemovedFromTeam : TeamEvent

    data object RemoveFailed : TeamEvent
}
