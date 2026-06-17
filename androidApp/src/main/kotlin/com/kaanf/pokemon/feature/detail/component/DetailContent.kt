package com.kaanf.pokemon.feature.detail.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kaanf.pokemon.designsystem.chip.getPokemonTypeColors
import com.kaanf.pokemon.domain.model.PokemonType
import com.kaanf.pokemon.feature.detail.layout.DetailAdaptiveLayout
import com.kaanf.pokemon.presentation.detail.model.DetailUiModel

@Composable
internal fun DetailContent(
    detail: DetailUiModel,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val typeColors = getPokemonTypeColors(
        detail.types.firstOrNull() ?: PokemonType.UNKNOWN
    )

    DetailAdaptiveLayout(
        backgroundColor = typeColors.solid,
        contentPadding = contentPadding,
        modifier = modifier,
        artwork = { artworkModifier ->
            DetailArtwork(
                modifier = artworkModifier,
                imageUrl = detail.imageUrl,
                shinyImageUrl = detail.shinyImageUrl,
                backgroundColor = typeColors.solid,
                topPadding = contentPadding.calculateTopPadding(),
            )
        },
        info = {
            DetailInfo(
                detail = detail,
                typeColors = typeColors,
            )
        },
    )
}
