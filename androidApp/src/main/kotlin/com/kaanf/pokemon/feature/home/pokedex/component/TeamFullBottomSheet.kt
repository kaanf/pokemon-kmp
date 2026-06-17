package com.kaanf.pokemon.feature.home.pokedex.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.pokemon.designsystem.PokemonTheme
import com.kaanf.pokemon.designsystem.adaptive.isLandscape
import com.kaanf.pokemon.designsystem.dialog.AppDialog
import com.kaanf.pokemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamFullBottomSheet(
    onDismiss: () -> Unit,
) {
    val isLandscape = isLandscape()

    if (isLandscape) {
        AppDialog(onDismiss = onDismiss) {
            TeamFullContent(onClose = onDismiss)
        }
    } else {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 14.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(24.dp),
            containerColor = PokemonTheme.extendedColors.surfaceSecondary,
            dragHandle = null,
        ) {
            TeamFullContent(onClose = onDismiss)
        }
    }
}

@Composable
private fun TeamFullContent(
    onClose: () -> Unit,
) {
    val isLandscape = isLandscape()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(if (isLandscape) 8.dp else 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_pokedex_box),
            contentDescription = stringResource(R.string.cd_team_full_image),
            modifier = if (isLandscape) Modifier.height(96.dp) else Modifier,
        )

        Text(
            text = stringResource(R.string.team_full_title),
            style = if (isLandscape) {
                MaterialTheme.typography.headlineMedium
            } else {
                MaterialTheme.typography.headlineLarge
            },
            color = PokemonTheme.extendedColors.textPrimary,
        )

        Text(
            text = buildAnnotatedString {
                append(
                    stringResource(R.string.team_full_message_prefix)
                )

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                ) {
                    append(
                        stringResource(R.string.team_full_message_highlight)
                    )
                }

                append(
                    stringResource(R.string.team_full_message_suffix)
                )
            },
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            ),
            textAlign = TextAlign.Center,
            color = PokemonTheme.extendedColors.textTertiary,
        )


        Spacer(modifier = Modifier.height(6.dp))

        TeamFullCloseButton(
            onClick = onClose,
            isCompact = isLandscape
        )
    }
}

@Composable
private fun TeamFullCloseButton(
    onClick: () -> Unit,
    isCompact: Boolean = false,
) {
    Box(
        modifier = Modifier
            .then(if (isCompact) Modifier.widthIn(max = 240.dp) else Modifier)
            .fillMaxWidth()
            .clip(
                shape = CircleShape
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PokemonTheme.extendedColors.buttonGradientStart,
                        PokemonTheme.extendedColors.buttonGradientEnd,
                    ),
                ),
            )
            .clickable(onClick = onClick)
            .padding(vertical = if (isCompact) 10.dp else 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.action_close),
            style = if (isCompact) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.headlineMedium
            },
            color = PokemonTheme.extendedColors.textNegative
        )
    }
}

@Preview(
    name = "Pixel 4",
    device = Devices.PIXEL_4,
    showBackground = true
)
@Preview(
    name = "Pixel 7 Pro",
    device = Devices.PIXEL_7_PRO,
    showBackground = true
)
@Composable
@PreviewLightDark
@PreviewScreenSizes
private fun TeamFullBottomSheetPreview() {
    PokemonTheme {
        TeamFullContent(onClose = {})
    }
}