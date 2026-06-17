package com.kaanf.pokemon.feature.detail.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.adaptive.isLandscape

@Composable
internal fun DetailAdaptiveLayout(
    backgroundColor: Color,
    contentPadding: PaddingValues,
    artwork: @Composable (Modifier) -> Unit,
    info: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    when (isLandscape()) {
        false -> {
            Column(
                modifier = modifier.verticalScroll(rememberScrollState()),
            ) {
                artwork(Modifier)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .background(
                            color = PokemonTheme.extendedColors.surfaceSecondary,
                            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        )
                        .padding(bottom = contentPadding.calculateBottomPadding())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    content = info,
                )
            }
        }

        true -> {
            Row(modifier = modifier.fillMaxSize()) {
                artwork(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(top = contentPadding.calculateTopPadding())
                        .padding(bottom = contentPadding.calculateBottomPadding())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    content = info,
                )
            }
        }
    }
}
