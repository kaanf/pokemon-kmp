package com.kaanf.pokemon.designsystem.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaanf.pokemon.R

@Composable
fun RoundedIcon(
    iconPath: Int,
    padding: Dp = 10.dp,
    onClick: (() -> Unit) = {},
    iconTint: Color,
    iconBackground: Color,
) {
    Icon(
        painter = painterResource(iconPath),
        contentDescription = stringResource(R.string.cd_icon),
        tint = iconTint,
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = iconBackground)
            .padding(all = padding)
            .clickable(onClick = onClick)
    )
}