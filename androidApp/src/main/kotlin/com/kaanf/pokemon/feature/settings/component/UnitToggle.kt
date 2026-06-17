package com.kaanf.pokemon.feature.settings.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.domain.model.UnitSystem

@Composable
internal fun UnitToggle(
    selected: UnitSystem,
    onSelect: (UnitSystem) -> Unit,
) {
    val options = listOf(
        UnitSystem.METRIC to stringResource(R.string.unit_metric),
        UnitSystem.IMPERIAL to stringResource(R.string.unit_imperial),
    )
    val selectedIndex = options.indexOfFirst { it.first == selected }.coerceAtLeast(0)

    BoxWithConstraints(
        modifier = Modifier
            .width(180.dp)
            .height(32.dp)
            .clip(CircleShape)
            .background(PokemonTheme.extendedColors.surfaceSecondary)
            .padding(3.dp),
    ) {
        val itemWidth = maxWidth / options.size
        val indicatorOffset by animateDpAsState(
            targetValue = itemWidth * selectedIndex,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium,
            ),
            label = "unitIndicatorOffset",
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(x = indicatorOffset.roundToPx(), y = 0) }
                .width(itemWidth)
                .fillMaxHeight()
                .background(
                    color = PokemonTheme.extendedColors.surfacePrimary,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = PokemonTheme.extendedColors.surfaceStroke,
                    shape = CircleShape
                ),
        )

        Row(modifier = Modifier.fillMaxSize()) {
            options.forEachIndexed { index, (unit, label) ->
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onSelect(unit) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (index == selectedIndex) {
                            PokemonTheme.extendedColors.textPrimary
                        } else {
                            PokemonTheme.extendedColors.textSecondary
                        },
                    )
                }
            }
        }
    }
}
