package com.kaanf.pokemon.feature.home.pokedex.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.presentation.util.formatPokedexNumber
import com.kaanf.pokemon.presentation.util.toFormattedName

@Composable
fun PokemonGridCard(
    pokemon: Pokemon,
    isInTeam: Boolean,
    isLandscape: Boolean,
    onClick: () -> Unit,
    onAddToTeam: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .background(
                color = PokemonTheme.extendedColors.surfacePrimary,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = PokemonTheme.extendedColors.surfaceStroke,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(
                all = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PokemonTheme.extendedColors.surfaceSecondary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = stringResource(R.string.cd_pokemon_image_grid),
                modifier = Modifier
                    .padding(
                        all = 18.dp
                    )
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .align(
                        alignment = Alignment.Center
                    )
            )

            Icon(
                painter = painterResource(R.drawable.ic_pokeball),
                contentDescription = stringResource(R.string.cd_add_to_team),
                tint = if (isInTeam) {
                    PokemonTheme.extendedColors.pokeball
                } else {
                    PokemonTheme.extendedColors.textSecondary
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onAddToTeam)
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = pokemon.name.toFormattedName(),
            style = if (isLandscape) {
                MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp, lineHeight = 20.sp)
            } else {
                MaterialTheme.typography.headlineMedium
            },
            color = PokemonTheme.extendedColors.textPrimary,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = formatPokedexNumber(pokemon.id),
            style = if (isLandscape) {
                PokemonTheme.extendedTypography.monoMedium.copy(fontSize = 13.sp, lineHeight = 16.sp)
            } else {
                PokemonTheme.extendedTypography.monoMedium
            },
            color = PokemonTheme.extendedColors.textSecondary,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
