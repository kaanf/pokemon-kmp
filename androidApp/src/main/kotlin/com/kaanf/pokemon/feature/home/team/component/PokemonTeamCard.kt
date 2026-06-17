package com.kaanf.pokemon.feature.home.team.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import coil3.compose.AsyncImage
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.chip.PokemonTypeChip
import com.kaanf.pokemon.domain.model.TeamMember
import com.kaanf.pokemon.presentation.util.toFormattedName
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PokemonTeamCard(
    member: TeamMember,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(
                    color = PokemonTheme.extendedColors.surfaceSecondary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            AsyncImage(
                model = member.pokemon.imageUrl,
                contentDescription = stringResource(R.string.cd_pokemon_image_team),
                modifier = Modifier
                    .padding(
                        all = 8.dp
                    )
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .align(
                        alignment = Alignment.Center
                    )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = member.pokemon.name.toFormattedName(),
                style = MaterialTheme.typography.headlineMedium,
                color = PokemonTheme.extendedColors.textPrimary,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                member.types.forEach { type ->
                    PokemonTypeChip(type = type)
                }
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Text(
                text = stringResource(
                    R.string.team_member_added,
                    formatAddedDate(member.addedAtEpochMillis),
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = PokemonTheme.extendedColors.textSecondary,
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = stringResource(R.string.cd_settings),
            tint = PokemonTheme.extendedColors.surfacePrimary,
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    color = PokemonTheme.extendedColors.textPrimary,
                )
                .clickable(onClick = onRemove)
        )
    }
}

private val addedDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMM d, yyyy")

private fun formatAddedDate(epochMillis: Long): String {
    return Instant.ofEpochMilli(epochMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(addedDateFormatter)
}
