package com.kaanf.pokemon.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kaanf.pokemon.designsystem.error.ErrorState
import com.kaanf.pokemon.designsystem.scaffold.AppScaffold
import com.kaanf.pokemon.designsystem.topbar.AppTopBar
import com.kaanf.pokemon.feature.detail.component.DetailContent
import com.kaanf.pokemon.feature.settings.SettingsBottomSheet
import com.kaanf.pokemon.presentation.detail.DetailAction
import com.kaanf.pokemon.presentation.detail.DetailState
import com.kaanf.pokemon.presentation.detail.DetailViewModel
import com.kaanf.pokemon.presentation.model.AppTopBarState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreenRoot(
    pokemonId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: DetailViewModel = koinViewModel { parametersOf(pokemonId) }

    val state by viewModel.state.collectAsStateWithLifecycle()
    var isSettingsShown by remember { mutableStateOf(false) }

    AppScaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                state = AppTopBarState.Detail,
                onBackClick = onBack,
                onSettingsClick = { isSettingsShown = true },
            )
        },
    ) { innerPadding ->
        DetailScreen(
            state = state,
            onRetry = { viewModel.onAction(DetailAction.OnRetry) },
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize(),
        )
    }

    if (isSettingsShown) {
        SettingsBottomSheet(onDismiss = { isSettingsShown = false })
    }
}

@Composable
private fun DetailScreen(
    state: DetailState,
    onRetry: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val detail = state.detail
    val error = state.error
    when {
        detail != null -> DetailContent(
            detail = detail,
            contentPadding = contentPadding,
            modifier = modifier,
        )

        state.isLoading -> Box(
            modifier = modifier.padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }

        error != null -> ErrorState(
            error = error,
            onRetry = onRetry,
            modifier = modifier.padding(contentPadding),
        )
    }
}
