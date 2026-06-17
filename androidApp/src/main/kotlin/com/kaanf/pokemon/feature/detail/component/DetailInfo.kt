package com.kaanf.pokemon.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.chip.PokemonTypeChip
import com.kaanf.pokemon.designsystem.chip.PokemonTypeColors
import com.kaanf.pokemon.presentation.detail.model.DetailUiModel
import com.kaanf.pokemon.presentation.util.formatPokedexNumber
import com.kaanf.pokemon.presentation.util.toFormattedName

@Composable
internal fun DetailInfo(
    detail: DetailUiModel,
    typeColors: PokemonTypeColors,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            detail.types.forEach { type ->
                PokemonTypeChip(type = type)
            }
        }

        Text(
            text = formatPokedexNumber(detail.id),
            style = PokemonTheme.extendedTypography.monoLarge,
            color = PokemonTheme.extendedColors.textSecondary,
        )
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = detail.name.toFormattedName(),
        style = MaterialTheme.typography.headlineLarge,
        color = PokemonTheme.extendedColors.textPrimary,
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DetailSection(
            typeColors = typeColors,
            icon = R.drawable.ic_weight,
            label = stringResource(R.string.detail_weight),
            value = detail.weight,
            modifier = Modifier.weight(1f),
        )

        DetailSection(
            typeColors = typeColors,
            icon = R.drawable.ic_ruler,
            label = stringResource(R.string.detail_height),
            value = detail.height,
            modifier = Modifier.weight(1f),
        )
    }

    DetailSection(
        typeColors = typeColors,
        icon = R.drawable.ic_move,
        label = stringResource(R.string.detail_moves),
        value = detail.abilities
            .filterNot { it.isHidden }
            .joinToString("\n") { it.name.toFormattedName() },
    )

    BaseStatsSection(
        stats = detail.stats,
        accentColor = typeColors.solid,
    )
}
