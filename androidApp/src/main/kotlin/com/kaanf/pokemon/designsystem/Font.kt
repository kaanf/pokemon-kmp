package com.kaanf.pokemon.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kaanf.pokemon.R

private val Inter: FontFamily
    get() = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_extrabold, FontWeight.ExtraBold),
    )

private val ShareTechMono: FontFamily
    get() = FontFamily(Font(R.font.share_tech_mono, FontWeight.Normal))

val PokemonTypography = Typography().run {
    copy(
        displaySmall = displaySmall.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 38.sp,
            lineHeight = 48.sp,
            letterSpacing = 0.sp,
        ),
        headlineLarge = headlineLarge.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.047).em,
        ),
        headlineMedium = headlineMedium.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        titleLarge = titleLarge.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = titleMedium.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
        titleSmall = titleSmall.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
        bodyLarge = bodyLarge.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        ),
        bodyMedium = bodyMedium.copy(
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        ),
    )
}

@Immutable
data class ExtendedTypography(
    val monoLarge: TextStyle,
    val monoMedium: TextStyle,
)

val PokemonExtendedTypography = ExtendedTypography(
    monoLarge = TextStyle(
        fontFamily = ShareTechMono,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    monoMedium = TextStyle(
        fontFamily = ShareTechMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
)
