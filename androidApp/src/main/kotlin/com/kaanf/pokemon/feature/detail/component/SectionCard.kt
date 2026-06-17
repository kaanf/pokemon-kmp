package com.kaanf.pokemon.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.designsystem.PokemonTheme

@Composable
internal fun SectionCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PokemonTheme.extendedColors.surfacePrimary, RoundedCornerShape(24.dp))
            .border(1.dp, PokemonTheme.extendedColors.surfaceStroke, RoundedCornerShape(24.dp))
            .padding(16.dp),
        content = content,
    )
}
