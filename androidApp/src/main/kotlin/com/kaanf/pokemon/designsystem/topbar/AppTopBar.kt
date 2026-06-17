package com.kaanf.pokemon.designsystem.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.presentation.model.AppTopBarState

@Composable
fun AppTopBar(
    state: AppTopBarState,
    onBackClick: (() -> Unit) = {},
    onSettingsClick: (() -> Unit) = {},
) {
    val isLandscape = isLandscape()
    val verticalPadding = if (isLandscape) 8.dp else 16.dp
    val titleStyle = if (isLandscape) {
        MaterialTheme.typography.headlineMedium
    } else {
        MaterialTheme.typography.headlineLarge
    }

    Column(
        modifier = Modifier
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = verticalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (state != AppTopBarState.Detail) {
                Text(
                    text = when (state) {
                        AppTopBarState.List -> {
                            stringResource(R.string.app_name)
                        }

                        is AppTopBarState.Team -> {
                            stringResource(R.string.top_bar_team_count, state.teamCount)
                        }
                    },
                    style = titleStyle,
                    color = PokemonTheme.extendedColors.textPrimary,
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_left_chevron),
                    contentDescription = stringResource(R.string.cd_back),
                    tint = PokemonTheme.extendedColors.textPrimary,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = PokemonTheme.extendedColors.surfacePrimary)
                        .padding(all = 10.dp)
                        .clickable(onClick = onBackClick)
                )
            }

            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = stringResource(R.string.cd_settings),
                tint = PokemonTheme.extendedColors.textPrimary,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = PokemonTheme.extendedColors.surfacePrimary)
                    .padding(all = 10.dp)
                    .clickable(onClick = onSettingsClick)
            )
        }

        if (state != AppTopBarState.Detail) {
            HorizontalDivider(
                color = PokemonTheme.extendedColors.surfaceStroke
            )
        }
    }
}