package com.kaanf.pokemon.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.feature.home.layout.AdaptiveNavScaffold
import com.kaanf.pokemon.designsystem.topbar.AppTopBar
import com.kaanf.pokemon.feature.home.pokedex.PokedexScreenRoot
import com.kaanf.pokemon.feature.home.team.TeamScreenRoot
import com.kaanf.pokemon.feature.settings.SettingsBottomSheet
import com.kaanf.pokemon.presentation.home.HomeViewModel
import com.kaanf.pokemon.presentation.model.AppTopBarState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val pagerState = rememberPagerState(initialPage = 0) { BottomTab.entries.size }
    val coroutineScope = rememberCoroutineScope()
    val teamCount by homeViewModel.teamCount.collectAsStateWithLifecycle()
    var showSettings by remember { mutableStateOf(false) }

    val selectedTab = BottomTab.entries[pagerState.currentPage]
    val onTabSelected: (BottomTab) -> Unit = { tab ->
        coroutineScope.launch { pagerState.animateScrollToPage(tab.ordinal) }
    }
    val topBarState = when (selectedTab) {
        BottomTab.POKEDEX -> AppTopBarState.List
        BottomTab.TEAM -> AppTopBarState.Team(teamCount)
    }

    AdaptiveNavScaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                state = topBarState,
                onSettingsClick = { showSettings = true },
            )
        },
        navigationBar = {
            HomeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        },
        navigationRail = {
            HomeNavigationRail(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        },
    ) { innerPadding ->
        HomePager(
            pagerState = pagerState,
            onPokemonClick = onPokemonClick,
            modifier = Modifier.padding(innerPadding),
        )
    }

    if (showSettings) {
        SettingsBottomSheet(
            onDismiss = { showSettings = false }
        )
    }
}

@Composable
private fun HomePager(
    pagerState: PagerState,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
    ) { page ->
        when (BottomTab.entries[page]) {
            BottomTab.POKEDEX -> PokedexScreenRoot(
                onPokemonClick = onPokemonClick,
                modifier = Modifier.fillMaxSize(),
            )

            BottomTab.TEAM -> TeamScreenRoot(
                onPokemonClick = onPokemonClick,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun HomeBottomBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
) {
    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = PokemonTheme.extendedColors.surfaceStroke,
        )

        NavigationBar(
            containerColor = PokemonTheme.extendedColors.surfaceSecondary,
        ) {
            BottomTab.entries.forEach { tab ->
                val selected = selectedTab == tab
                val label = stringResource(tab.labelRes)
                NavigationBarItem(
                    selected = selected,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = PokemonTheme.extendedColors.surfaceTertiary,
                        unselectedIconColor = PokemonTheme.extendedColors.textPrimary,
                        selectedIconColor = PokemonTheme.extendedColors.textPrimary,
                        unselectedTextColor = PokemonTheme.extendedColors.textPrimary,
                        selectedTextColor = PokemonTheme.extendedColors.textPrimary,
                    ),
                    onClick = { onTabSelected(tab) },
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (selected) tab.selectedIconRes else tab.iconRes,
                            ),
                            contentDescription = label,
                        )
                    },
                    label = { Text(label) },
                )
            }
        }
    }
}

@Composable
private fun HomeNavigationRail(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
) {
    NavigationRail(
        containerColor = PokemonTheme.extendedColors.surfaceSecondary,
    ) {
        BottomTab.entries.forEach { tab ->
            val selected = selectedTab == tab
            val label = stringResource(tab.labelRes)
            NavigationRailItem(
                selected = selected,
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = PokemonTheme.extendedColors.surfaceTertiary,
                    unselectedIconColor = PokemonTheme.extendedColors.textPrimary,
                    selectedIconColor = PokemonTheme.extendedColors.textPrimary,
                    unselectedTextColor = PokemonTheme.extendedColors.textPrimary,
                    selectedTextColor = PokemonTheme.extendedColors.textPrimary,
                ),
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (selected) tab.selectedIconRes else tab.iconRes,
                        ),
                        contentDescription = label,
                    )
                },
                label = { Text(label) },
            )
        }
    }
}
