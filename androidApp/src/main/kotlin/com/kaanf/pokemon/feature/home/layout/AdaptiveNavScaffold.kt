package com.kaanf.pokemon.feature.home.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
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
    if (isLandscape()) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(PokemonTheme.extendedColors.surfaceSecondary),
        ) {
            navigationRail()

            VerticalDivider(color = PokemonTheme.extendedColors.surfaceStroke)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.End + WindowInsetsSides.Bottom,
                        ),
                    ),
            ) {
                topBar()

                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    content(PaddingValues())
                }
            }
        }
    } else {
        AppScaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = navigationBar,
            content = content,
        )
    }
}
