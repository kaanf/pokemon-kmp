package com.kaanf.pokemon.feature.settings.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme

@Composable
internal fun AboutRow() {
    var showDialog by remember { mutableStateOf(false) }

    SettingsRow(
        title = stringResource(R.string.settings_about),
        onClick = { showDialog = true },
        icon = R.drawable.ic_info
    ) {
        Text(
            text = "›",
            style = MaterialTheme.typography.titleLarge,
            color = PokemonTheme.extendedColors.textTertiary,
        )
    }

    if (showDialog) {
        AboutDialog(onDismiss = { showDialog = false })
    }
}
