package com.kaanf.pokemon.domain.model

data class PokemonStat(
    val type: StatType,
    val value: Int,
) {
    companion object {
        const val MAX_BASE_STAT = 255
    }
}

enum class StatType(val apiName: String) {
    HP("hp"),
    ATTACK("attack"),
    DEFENSE("defense"),
    SPECIAL_ATTACK("special-attack"),
    SPECIAL_DEFENSE("special-defense"),
    SPEED("speed"),
    OTHER("");

    companion object {
        fun fromApiName(name: String): StatType {
            return entries.firstOrNull { it.apiName == name } ?: OTHER
        }
    }
}
