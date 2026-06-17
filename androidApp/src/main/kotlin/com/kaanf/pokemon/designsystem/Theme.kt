package com.kaanf.pokemon.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textNegative: Color,
    val surfacePrimary: Color,
    val surfaceSecondary: Color,
    val surfaceTertiary: Color,
    val surfaceStroke: Color,
    val navigationPrimary: Color,
    val navigationSecondary: Color,
    val navigationMono: Color,
    val pokeball: Color,
    val buttonTextColor: Color,
    val buttonGradientStart: Color,
    val buttonGradientEnd: Color,
)

private val LightExtendedColors = ExtendedColors(
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textNegative = TextNegative,
    surfacePrimary = SurfacePrimary,
    surfaceSecondary = SurfaceSecondary,
    surfaceTertiary = SurfaceTertiary,
    surfaceStroke = SurfaceStroke,
    navigationPrimary = NavigationPrimary,
    navigationSecondary = NavigationSecondary,
    navigationMono = NavigationMono,
    pokeball = Pokeball,
    buttonTextColor = ButtonTextColor,
    buttonGradientStart = ButtonGradientStart,
    buttonGradientEnd = ButtonGradientEnd,
)

private val DarkExtendedColors = ExtendedColors(
    textPrimary = TextPrimaryDark,
    textSecondary = TextSecondaryDark,
    textTertiary = TextTertiaryDark,
    textNegative = TextNegativeDark,
    surfacePrimary = SurfacePrimaryDark,
    surfaceSecondary = SurfaceSecondaryDark,
    surfaceTertiary = SurfaceTertiaryDark,
    surfaceStroke = SurfaceStrokeDark,
    navigationPrimary = NavigationPrimaryDark,
    navigationSecondary = NavigationSecondaryDark,
    navigationMono = NavigationMono,
    pokeball = Pokeball,
    buttonTextColor = ButtonTextColorDark,
    buttonGradientStart = ButtonGradientStartDark,
    buttonGradientEnd = ButtonGradientEndDark,
)

private val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }
private val LocalExtendedTypography = staticCompositionLocalOf { PokemonExtendedTypography }

object PokemonTheme {
    val extendedColors: ExtendedColors
        @Composable @ReadOnlyComposable get() = LocalExtendedColors.current

    val extendedTypography: ExtendedTypography
        @Composable @ReadOnlyComposable get() = LocalExtendedTypography.current
}

@Composable
fun PokemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
        LocalExtendedTypography provides PokemonExtendedTypography,
    ) {
        MaterialTheme(
            typography = PokemonTypography,
            content = content,
        )
    }
}
