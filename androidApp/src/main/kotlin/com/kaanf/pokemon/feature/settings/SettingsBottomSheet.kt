package com.kaanf.pokemon.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.designsystem.dialog.AppDialog
import com.kaanf.pokemon.domain.model.TimeFormat
import com.kaanf.pokemon.feature.settings.component.AboutRow
import com.kaanf.pokemon.feature.settings.component.ModeDropdown
import com.kaanf.pokemon.feature.settings.component.SettingsRow
import com.kaanf.pokemon.feature.settings.component.UnitToggle
import com.kaanf.pokemon.presentation.settings.SettingsAction
import com.kaanf.pokemon.presentation.settings.SettingsState
import com.kaanf.pokemon.presentation.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isLandscape = isLandscape()

    if (isLandscape) {
        AppDialog(onDismiss = onDismiss) {
            SettingsContent(
                state = state,
                onAction = viewModel::onAction,
            )
        }
    } else {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = PokemonTheme.extendedColors.surfaceSecondary,
        ) {
            SettingsContent(
                state = state,
                onAction = viewModel::onAction,
            )
        }
    }
}

@Composable
private fun SettingsContent(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    val settings = state.settings
    val isLandscape = isLandscape()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(top = if (isLandscape) 20.dp else 0.dp)
            .padding(bottom = if (isLandscape) 12.dp else 24.dp),
        verticalArrangement = Arrangement.spacedBy(if (isLandscape) 8.dp else 12.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.titleLarge,
            color = PokemonTheme.extendedColors.textPrimary,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PokemonTheme.extendedColors.surfacePrimary,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            SettingsRow(title = stringResource(R.string.settings_unit), icon = R.drawable.ic_ruler) {
                UnitToggle(
                    selected = settings.unitSystem,
                    onSelect = { onAction(SettingsAction.OnUnitSystemSelect(it)) },
                )
            }

            SettingsDivider()

            SettingsRow(title = stringResource(R.string.settings_time_24_hour), icon = R.drawable.ic_time) {
                Switch(
                    checked = settings.timeFormat == TimeFormat.TWENTY_FOUR_HOUR,
                    onCheckedChange = { checked ->
                        onAction(
                            SettingsAction.OnTimeFormatSelect(
                                if (checked) TimeFormat.TWENTY_FOUR_HOUR else TimeFormat.TWELVE_HOUR
                            )
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF34C759),
                        checkedBorderColor = Color.Transparent,
                        uncheckedThumbColor = PokemonTheme.extendedColors.textTertiary,
                        uncheckedTrackColor = PokemonTheme.extendedColors.surfacePrimary,
                        uncheckedBorderColor = PokemonTheme.extendedColors.textTertiary,
                    ),
                )
            }

            SettingsDivider()

            SettingsRow(title = stringResource(R.string.settings_mode), icon = R.drawable.ic_mode) {
                ModeDropdown(
                    selected = settings.themeMode,
                    onSelect = { onAction(SettingsAction.OnThemeModeSelect(it)) },
                )
            }

            SettingsDivider()

            AboutRow()
        }
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(color = PokemonTheme.extendedColors.surfaceStroke)
}

@Preview
@Composable
fun SettingsBottomSheetPreview() {
    PokemonTheme {
        SettingsContent(
            state = SettingsState(),
            onAction = {}
        )
    }
}