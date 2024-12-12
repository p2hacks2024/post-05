package com.example.flush.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.flush.ui.theme.dimensions.IconSize

@Composable
fun Image(
    @DrawableRes resId: Int,
    modifier: Modifier = Modifier,
    size: Dp = IconSize.Small,
) {
    androidx.compose.foundation.Image(
        painter = painterResource(resId),
        contentDescription = null,
        modifier = modifier.size(size),
    )
}
