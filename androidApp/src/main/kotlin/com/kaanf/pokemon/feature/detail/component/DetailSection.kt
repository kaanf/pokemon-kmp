package com.kaanf.pokemon.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.chip.PokemonTypeColors
import com.kaanf.pokemon.designsystem.icon.RoundedIcon
import com.kaanf.pokemon.domain.model.PokemonStat
import com.kaanf.pokemon.domain.model.StatType
import com.kaanf.pokemon.presentation.util.shortLabel

@Composable
fun DetailSection(
    typeColors: PokemonTypeColors,
    icon: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    SectionCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
                color = PokemonTheme.extendedColors.textSecondary,
            )

            RoundedIcon(
                iconPath = icon,
                iconTint = typeColors.onContainer,
                iconBackground = typeColors.container,
                padding = 8.dp,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            color = PokemonTheme.extendedColors.textPrimary,
        )
    }
}

@Composable
fun BaseStatsSection(
    stats: List<PokemonStat>,
    accentColor: Color,
) {
    SectionCard {
        Text(
            text = stringResource(R.string.detail_base_stats),
            style = MaterialTheme.typography.titleLarge,
            color = PokemonTheme.extendedColors.textSecondary,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            stats
                .filter { it.type != StatType.OTHER }
                .forEach { stat ->
                    StatRow(
                        stat = stat,
                        accentColor = accentColor,
                    )
                }
        }
    }
}

@Composable
private fun StatRow(
    stat: PokemonStat,
    accentColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stat.type.shortLabel(),
            style = MaterialTheme.typography.titleSmall,
            color = PokemonTheme.extendedColors.textPrimary,
            modifier = Modifier.width(56.dp),
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(accentColor.copy(alpha = 0.2f)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(
                        fraction = (stat.value.toFloat() / PokemonStat.MAX_BASE_STAT)
                            .coerceIn(0f, 1f)
                    )
                    .background(accentColor, RoundedCornerShape(4.dp)),
            )
        }

        Text(
            text = stat.value.toString(),
            textAlign = TextAlign.End,
            modifier = Modifier.width(48.dp),
            style = MaterialTheme.typography.titleSmall,
            color = PokemonTheme.extendedColors.textSecondary,
        )
    }
}
