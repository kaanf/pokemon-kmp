package com.kaanf.pokemon.presentation.util

import com.kaanf.pokemon.domain.model.StatType

fun StatType.shortLabel(): String {
    return when (this) {
        StatType.HP -> "HP"
        StatType.ATTACK -> "ATK"
        StatType.DEFENSE -> "DEF"
        StatType.SPECIAL_ATTACK -> "SATK"
        StatType.SPECIAL_DEFENSE -> "SDEF"
        StatType.SPEED -> "SPD"
        StatType.OTHER -> ""
    }
}
