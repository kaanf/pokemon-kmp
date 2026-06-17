package com.kaanf.pokemon

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.snackbar.LocalSnackbarController
import com.kaanf.pokemon.designsystem.snackbar.PokemonSnackbar
import com.kaanf.pokemon.designsystem.snackbar.rememberSnackbarController
import com.kaanf.pokemon.domain.model.ThemeMode
import com.kaanf.pokemon.navigation.PokemonNavHost
import com.kaanf.pokemon.presentation.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
    settingsViewModel: SettingsViewModel = koinViewModel(),
) {
    val settingsState by settingsViewModel.state.collectAsState()
    val darkTheme = when (settingsState.settings.themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    PokemonTheme(darkTheme = darkTheme) {
        SystemBarsEffects(darkTheme)
        val snackbarController = rememberSnackbarController()

        CompositionLocalProvider(LocalSnackbarController provides snackbarController) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PokemonTheme.extendedColors.surfaceSecondary),
            ) {
                PokemonNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                )

                SnackbarHost(
                    hostState = snackbarController.hostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp),
                    snackbar = { data -> PokemonSnackbar(data) },
                )
            }
        }
    }
}

@Composable
private fun SystemBarsEffects(isDarkTheme: Boolean) {
    val view = LocalView.current
    if (view.isInEditMode) return

    val orientation = LocalConfiguration.current.orientation
    val navBarColor = PokemonTheme.extendedColors.surfaceSecondary.toArgb()

    DisposableEffect(isDarkTheme, orientation) {
        val activity = view.context as ComponentActivity
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
                detectDarkMode = { isDarkTheme },
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = navBarColor,
                darkScrim = navBarColor,
                detectDarkMode = { isDarkTheme }
            )
        )

        onDispose {}
    }
}