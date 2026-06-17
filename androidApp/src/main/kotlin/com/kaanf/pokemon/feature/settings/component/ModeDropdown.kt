package com.kaanf.pokemon.feature.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.domain.model.ThemeMode

@Composable
internal fun ModeDropdown(
    selected: ThemeMode,
    onSelect: (ThemeMode) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var anchorHeight by remember { mutableStateOf(0) }

    Box {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .onSizeChanged { anchorHeight = it.height }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selected.label(),
                style = MaterialTheme.typography.titleMedium,
                color = PokemonTheme.extendedColors.textTertiary,
            )

            Text(
                text = "⌄",
                style = MaterialTheme.typography.titleLarge,
                color = PokemonTheme.extendedColors.textTertiary,
            )
        }

        if (expanded) {
            Popup(
                alignment = Alignment.TopEnd,
                offset = IntOffset(x = 0, y = anchorHeight),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true),
            ) {
                ModeDropdownMenu(
                    selected = selected,
                    onSelect = {
                        onSelect(it)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun ModeDropdownMenu(
    selected: ThemeMode,
    onSelect: (ThemeMode) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = PokemonTheme.extendedColors.surfacePrimary,
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .width(240.dp)
                .padding(vertical = 4.dp),
        ) {
            ThemeMode.entries.forEach { mode ->
                ModeDropdownItem(
                    label = mode.label(),
                    isSelected = mode == selected,
                    onClick = { onSelect(mode) },
                )
            }
        }
    }
}

@Composable
private fun ModeDropdownItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.size(18.dp), contentAlignment = Alignment.Center) {
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = PokemonTheme.extendedColors.textPrimary,
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = PokemonTheme.extendedColors.textPrimary,
        )
    }
}

@Composable
@Preview
fun ModeDropdownPreview() {
    PokemonTheme {
        ModeDropdownMenu(
            selected = ThemeMode.SYSTEM
        ) { }
    }
}

@Composable
private fun ThemeMode.label(): String = when (this) {
    ThemeMode.SYSTEM -> stringResource(R.string.mode_system)
    ThemeMode.LIGHT -> stringResource(R.string.mode_light)
    ThemeMode.DARK -> stringResource(R.string.mode_dark)
}
