package com.kaanf.pokemon.feature.home.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.feature.home.team.component.rememberAddPokemonIcon
import com.kaanf.pokemon.designsystem.snackbar.LocalSnackbarController
import com.kaanf.pokemon.feature.home.team.component.PokemonTeamCard
import com.kaanf.pokemon.presentation.team.TeamAction
import com.kaanf.pokemon.presentation.team.TeamEvent
import com.kaanf.pokemon.presentation.team.TeamState
import com.kaanf.pokemon.presentation.team.TeamViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TeamScreenRoot(
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TeamViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarController = LocalSnackbarController.current

    val removedMessage = stringResource(R.string.toast_removed_from_team)
    val removeFailedMessage = stringResource(R.string.toast_remove_from_team_failed)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            val message = when (event) {
                TeamEvent.RemovedFromTeam -> removedMessage
                TeamEvent.RemoveFailed -> removeFailedMessage
            }
            snackbarController.show(message)
        }
    }

    TeamScreen(
        state = state,
        onAction = viewModel::onAction,
        onPokemonClick = onPokemonClick,
        modifier = modifier,
    )
}

@Composable
private fun TeamScreen(
    modifier: Modifier = Modifier,
    state: TeamState,
    onAction: (TeamAction) -> Unit,
    onPokemonClick: (Int) -> Unit,
) {
    if (state.members.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            AddMorePokemonCard()
        }
        return
    }

    val columns = if (isLandscape()) 2 else 1
    val isTeamFull = state.emptySlots == 0

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = state.members,
            key = { member -> member.pokemon.id },
        ) { member ->
            PokemonTeamCard(
                member = member,
                onClick = { onPokemonClick(member.pokemon.id) },
                onRemove = { onAction(TeamAction.OnRemoveMember(member.pokemon.id)) },
            )
        }

        if (!isTeamFull) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item(
                key = "add_pokemon_card",
                span = { GridItemSpan(maxLineSpan) },
            ) {
                AddMorePokemonCard()
            }
        }
    }
}

@Composable
private fun AddMorePokemonCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = rememberAddPokemonIcon(),
            contentDescription = stringResource(R.string.cd_add_pokemon)
        )

        Text(
            text = stringResource(R.string.team_empty_message),
            style = MaterialTheme.typography.headlineLarge,
            color = PokemonTheme.extendedColors.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}
