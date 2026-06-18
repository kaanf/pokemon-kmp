package com.kaanf.pokemon.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.feature.detail.DetailScreenRoot
import com.kaanf.pokemon.feature.home.HomeScreen

private const val NAV_TRANSITION_DURATION_MS = 300

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFrom(
    direction: SlideDirection,
): EnterTransition = slideIntoContainer(direction, tween(NAV_TRANSITION_DURATION_MS))

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutTo(
    direction: SlideDirection,
): ExitTransition = slideOutOfContainer(direction, tween(NAV_TRANSITION_DURATION_MS))

@Composable
fun PokemonNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val landscape = isLandscape()
    val forward = if (landscape) SlideDirection.Up else SlideDirection.Start
    val backward = if (landscape) SlideDirection.Down else SlideDirection.End

    NavHost(
        navController = navController,
        startDestination = PokemonGraph.Home,
        modifier = modifier,
        enterTransition = { slideInFrom(forward) },
        exitTransition = { slideOutTo(forward) },
        popEnterTransition = { slideInFrom(backward) },
        popExitTransition = { slideOutTo(backward) },
    ) {
        composable<PokemonGraph.Home> {
            HomeScreen(
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonGraph.Detail(pokemonId))
                },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable<PokemonGraph.Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<PokemonGraph.Detail>()
            DetailScreenRoot(
                pokemonId = detail.pokemonId,
                onBack = navController::popBackStack,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
