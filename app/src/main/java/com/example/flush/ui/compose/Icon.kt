package com.example.flush.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.example.flush.ui.theme.dimensions.IconSize

@Composable
fun Icon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = IconSize.Small,
    tint: Color = MaterialTheme.colorScheme.onSurface,
) {
    androidx.compose.material3.Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier.size(size),
        tint = tint,
    )
}
