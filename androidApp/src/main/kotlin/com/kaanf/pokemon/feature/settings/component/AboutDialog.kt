package com.kaanf.pokemon.feature.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.kaanf.pokemon.BuildConfig
import com.kaanf.pokemon.R
import com.kaanf.pokemon.designsystem.PokemonTheme

private const val AVATAR_URL = "https://avatars.githubusercontent.com/u/62348803?v=4"

@Composable
internal fun AboutDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .clip(RoundedCornerShape(24.dp))
                .background(PokemonTheme.extendedColors.surfaceSecondary)
                .padding(start = 24.dp, end = 8.dp, top = 8.dp, bottom = 32.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.settings_about),
                    style = MaterialTheme.typography.titleLarge,
                    color = PokemonTheme.extendedColors.textPrimary,
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        tint = PokemonTheme.extendedColors.textPrimary,
                        contentDescription = stringResource(R.string.cd_close),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AsyncImage(
                    model = AVATAR_URL,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape),
                )
                val tertiary = PokemonTheme.extendedColors.textTertiary
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.about_message))
                        append(" ")
                        withStyle(SpanStyle(color = tertiary.copy(alpha = 0.6f))) {
                            append(stringResource(R.string.about_message_deadline))
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = tertiary,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(R.string.about_thanks),
                    style = MaterialTheme.typography.bodySmall,
                    color = tertiary.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(R.string.about_version, BuildConfig.VERSION_NAME),
                    style = MaterialTheme.typography.bodyMedium,
                    color = tertiary,
                )
            }
        }
    }
}
