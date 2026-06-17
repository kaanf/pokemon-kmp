package com.kaanf.pokemon.presentation.detail

sealed interface DetailAction {
    data object OnRetry : DetailAction
}
