package com.example.flush.ui.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String?,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions:
    @Composable()
    (RowScope.() -> Unit) = {},
    contentColor: Color = MaterialTheme.colorScheme.primary,
) {
    TopAppBar(
        title = {
            title?.let {
                TitleLargeText(
                    text = title,
                    color = contentColor,
                )
            }
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
    )
}
