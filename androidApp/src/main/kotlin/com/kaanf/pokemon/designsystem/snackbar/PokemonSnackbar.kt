package com.kaanf.pokemon.designsystem.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.TypeGrass

@Composable
fun PokemonSnackbar(
    data: SnackbarData,
    modifier: Modifier = Modifier,
) {
    val colors = PokemonTheme.extendedColors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surfacePrimary)
            .border(1.dp, colors.surfaceStroke, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(colors.pokeball.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_pokeball),
                contentDescription = null,
                tint = colors.pokeball,
                modifier = Modifier.size(18.dp),
            )
        }

        Text(
            text = data.visuals.message,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 14.sp
            ),
            color = colors.textTertiary,
            modifier = Modifier.weight(1f),
        )

        data.visuals.actionLabel?.let { actionLabel ->
            Text(
                text = actionLabel,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                ),
                color = colors.pokeball,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { data.performAction() }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}