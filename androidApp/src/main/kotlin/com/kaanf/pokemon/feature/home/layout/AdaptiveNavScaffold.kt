package com.kaanf.pokemon.feature.home.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.designsystem.scaffold.AppScaffold

@Composable
fun AdaptiveNavScaffold(
    topBar: @Composable () -> Unit,
    navigationBar: @Composable () -> Unit,
    navigationRail: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val movableContent = remember(content) { movableContentOf(content) }

    if (isLandscape()) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(PokemonTheme.extendedColors.surfaceSecondary),
        ) {
            navigationRail()

            VerticalDivider(color = PokemonTheme.extendedColors.surfaceStroke)

            AppScaffold(
                modifier = Modifier.weight(1f),
                topBar = topBar,
                // The rail already handles the Start inset, so the content only
                // needs End + Bottom (Top is handled by the top bar itself).
                contentWindowInsets = WindowInsets.safeDrawing.only(
                    WindowInsetsSides.End + WindowInsetsSides.Bottom,
                ),
                content = movableContent,
            )
        }
    } else {
        AppScaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = navigationBar,
            content = movableContent,
        )
    }
}
