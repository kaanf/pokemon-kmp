package com.kaanf.pokemon.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class StatField(
    val name: String,
    val value: Int,
)

@Serializable
data class AbilityField(
    val name: String,
    val isHidden: Boolean,
)
