package com.kaanf.pokemon.feature.detail.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme


@Composable
internal fun DetailArtwork(
    imageUrl: String,
    shinyImageUrl: String,
    backgroundColor: Color,
    topPadding: Dp,
    modifier: Modifier = Modifier,
) {
    var isShiny by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = isShiny,
            transitionSpec = {
                val spec = tween<Float>(durationMillis = 300)
                fadeIn(spec).togetherWith(fadeOut(spec))
            },
            label = "shiny_artwork",
        ) { shiny ->
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(if (shiny) shinyImageUrl else imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.cd_pokemon_image_detail),
                modifier = Modifier
                    .padding(top = topPadding)
                    .padding(16.dp)
                    .size(250.dp),
            )
        }

        Switch(
            checked = isShiny,
            onCheckedChange = { isShiny = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFFFFB300),
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = PokemonTheme.extendedColors.surfaceSecondary,
                uncheckedTrackColor = PokemonTheme.extendedColors.textNegative,
                uncheckedBorderColor = Color.Transparent,
            ),
            thumbContent = {
                Icon(
                    painter = painterResource(R.drawable.ic_shiny),
                    contentDescription = stringResource(R.string.cd_toggle_shiny),
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
        )
    }
}
