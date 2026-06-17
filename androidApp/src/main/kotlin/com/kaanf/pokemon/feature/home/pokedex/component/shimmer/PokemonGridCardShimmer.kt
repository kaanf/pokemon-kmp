package com.kaanf.pokemon.feature.home.pokedex.component.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.shimmer.shimmerEffect

@Composable
fun PokemonGridCardShimmer(
    modifier: Modifier = Modifier,
) {
    val shimmerColor = PokemonTheme.extendedColors.surfaceSecondary
    val highlightColor = if (isSystemInDarkTheme()) {
        PokemonTheme.extendedColors.surfaceTertiary
    } else {
        PokemonTheme.extendedColors.surfacePrimary
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PokemonTheme.extendedColors.surfacePrimary,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = PokemonTheme.extendedColors.surfaceStroke,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(
                all = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shimmerEffect(baseColor = shimmerColor, highlightColor = highlightColor, shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(20.dp)
                .align(Alignment.Start)
                .shimmerEffect(baseColor = shimmerColor, highlightColor = highlightColor, shape = RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(14.dp)
                .align(Alignment.Start)
                .shimmerEffect(baseColor = shimmerColor, highlightColor = highlightColor, shape = RoundedCornerShape(4.dp))
        )
    }
}
