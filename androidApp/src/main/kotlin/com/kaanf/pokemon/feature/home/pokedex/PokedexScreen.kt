package com.kaanf.pokemon.feature.home.pokedex

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.designsystem.error.ErrorState
import com.kaanf.pokemon.designsystem.snackbar.LocalSnackbarController
import com.kaanf.pokemon.feature.home.pokedex.component.PokemonGridCard
import com.kaanf.pokemon.feature.home.pokedex.component.TeamFullBottomSheet
import com.kaanf.pokemon.feature.home.pokedex.component.shimmer.PokemonGridCardShimmer
import com.kaanf.pokemon.feature.home.pokedex.component.shimmer.ShimmerGrid
import com.kaanf.pokemon.presentation.pokedex.PokedexAction
import com.kaanf.pokemon.presentation.pokedex.PokedexEvent
import com.kaanf.pokemon.presentation.pokedex.PokedexState
import com.kaanf.pokemon.presentation.pokedex.PokedexViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokedexScreenRoot(
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokedexViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarController = LocalSnackbarController.current
    var showTeamFullSheet by remember { mutableStateOf(false) }

    val addedMessage = stringResource(R.string.toast_added_to_team)
    val removedMessage = stringResource(R.string.toast_removed_from_team)
    val addFailedMessage = stringResource(R.string.toast_add_to_team_failed)
    val removeFailedMessage = stringResource(R.string.toast_remove_from_team_failed)
    val pagingErrorMessage = stringResource(R.string.snackbar_paging_error)
    val retryLabel = stringResource(R.string.action_retry)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                PokedexEvent.TeamFull -> showTeamFullSheet = true
                PokedexEvent.AddedToTeam -> snackbarController.show(addedMessage)
                PokedexEvent.RemovedFromTeam -> snackbarController.show(removedMessage)
                PokedexEvent.AddToTeamFailed -> snackbarController.show(addFailedMessage)
                PokedexEvent.RemoveFromTeamFailed -> snackbarController.show(removeFailedMessage)

                is PokedexEvent.ShowPagingError ->
                    snackbarController.show(
                        message = pagingErrorMessage,
                        actionLabel = retryLabel,
                        onAction = { viewModel.onAction(PokedexAction.OnRetry) },
                    )
            }
        }
    }

    PokedexScreen(
        state = state,
        onAction = viewModel::onAction,
        onPokemonClick = onPokemonClick,
        modifier = modifier,
    )

    if (showTeamFullSheet) {
        TeamFullBottomSheet(onDismiss = { showTeamFullSheet = false })
    }
}

@Composable
private fun PokedexScreen(
    state: PokedexState,
    onAction: (PokedexAction) -> Unit,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pokemon = state.pokemon
    val error = state.error
    val showInitialShimmer = (state.isInitialLoading || (pokemon != null && pokemon.isEmpty())) &&
        error == null &&
        !state.endReached

    val columns = if (isLandscape()) 5 else 2

    when {
        error != null && pokemon.isNullOrEmpty() -> ErrorState(
            error = error,
            onRetry = { onAction(PokedexAction.OnRetry) },
            modifier = modifier,
        )

        else -> Crossfade(
            targetState = showInitialShimmer,
            animationSpec = tween(durationMillis = 300),
            label = "pokedexShimmerCrossfade",
            modifier = modifier,
        ) { shimmer ->
            if (shimmer) {
                ShimmerGrid(columns = columns)
            } else {
                PokemonGrid(
                    state = state,
                    onAction = onAction,
                    onPokemonClick = onPokemonClick,
                    columns = columns,
                )
            }
        }
    }
}

@Composable
private fun PokemonGrid(
    state: PokedexState,
    onAction: (PokedexAction) -> Unit,
    onPokemonClick: (Int) -> Unit,
    columns: Int,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    // Trigger the next page two rows before the end, matching iOS.
    val prefetchDistance = columns * 2
    val shouldLoadMore by remember(prefetchDistance) {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisible >= totalItems - prefetchDistance
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onAction(PokedexAction.OnLoadMore)
        }
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 4.dp
            ),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = state.pokemon.orEmpty(),
            key = { pokemon -> pokemon.id },
        ) { pokemon ->
            PokemonGridCard(
                pokemon = pokemon,
                isInTeam = pokemon.id in state.teamMemberIds,
                onClick = { onPokemonClick(pokemon.id) },
                onAddToTeam = { onAction(PokedexAction.OnAddToTeam(pokemon)) },
                modifier = Modifier.animateItem(),
            )
        }

        if (state.isLoadingMore) {
            items(
                count = columns,
                key = { index -> "load-more-shimmer-$index" },
            ) {
                PokemonGridCardShimmer(modifier = Modifier.animateItem())
            }
        }
    }
}
