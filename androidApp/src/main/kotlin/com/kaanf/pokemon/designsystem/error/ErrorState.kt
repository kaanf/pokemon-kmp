package com.kaanf.pokemon.designsystem.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R
import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.designsystem.PokemonTheme

@Composable
fun ErrorState(
    error: DataError.Remote,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isOffline = error == DataError.Remote.NO_INTERNET

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.img_sad_cubone),
            contentDescription = null,
            modifier = Modifier.height(140.dp),
        )

        Text(
            text = stringResource(
                if (isOffline) R.string.error_no_internet_title else R.string.error_generic_title,
            ),
            style = MaterialTheme.typography.headlineMedium,
            color = PokemonTheme.extendedColors.textPrimary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(
                if (isOffline) R.string.error_no_internet_message else R.string.error_generic_message,
            ),
            style = MaterialTheme.typography.titleMedium,
            color = PokemonTheme.extendedColors.textTertiary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        RetryButton(onClick = onRetry)
    }
}

@Composable
private fun RetryButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PokemonTheme.extendedColors.buttonGradientStart,
                        PokemonTheme.extendedColors.buttonGradientEnd,
                    ),
                ),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 28.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.action_retry),
            style = MaterialTheme.typography.titleMedium,
            color = PokemonTheme.extendedColors.buttonTextColor,
        )
    }
}

@PreviewLightDark
@Composable
private fun ErrorStatePreview() {
    PokemonTheme {
        ErrorState(error = DataError.Remote.NO_INTERNET, onRetry = {})
    }
}
