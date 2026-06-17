package com.kaanf.pokemon.feature.home

import androidx.annotation.StringRes
import com.kaanf.pokemon.R

enum class BottomTab(
    @StringRes val labelRes: Int,
    val iconRes: Int,
    val selectedIconRes: Int,
) {
    POKEDEX(
        labelRes = R.string.tab_pokedex,
        iconRes = R.drawable.ic_pokeball_bottom_tab,
        selectedIconRes = R.drawable.ic_pokeball_filled_bottom_tab,
    ),
    TEAM(
        labelRes = R.string.tab_team,
        iconRes = R.drawable.ic_shield_bottom_tab,
        selectedIconRes = R.drawable.ic_shield_filled_bottom_tab,
    ),
}
