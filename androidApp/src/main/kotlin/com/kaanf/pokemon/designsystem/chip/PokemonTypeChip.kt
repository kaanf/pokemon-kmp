package com.kaanf.pokemon.designsystem.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.domain.model.PokemonType
import com.kaanf.pokemon.presentation.util.toFormattedName

@Composable
fun PokemonTypeChip(type: PokemonType) {
    val colors = getPokemonTypeColors(type)
    Text(
        text = type.name.lowercase().toFormattedName(),
        style = PokemonTheme.extendedTypography.monoMedium.copy(
            fontSize = 14.sp
        ),
        color = colors.onContainer,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(colors.container)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}
