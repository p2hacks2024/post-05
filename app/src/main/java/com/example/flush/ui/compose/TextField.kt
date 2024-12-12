package com.example.flush.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.flush.ui.theme.dimensions.Alpha
import com.example.flush.ui.theme.dimensions.Paddings
import com.example.flush.ui.theme.dimensions.Weights
import java.util.concurrent.locks.Lock

@Composable
private fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    label: String = "",
    prefixIcon: @Composable (() -> Unit)? = null,
    suffixIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        shape = shape,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Paddings.Medium,
                    vertical = Paddings.Small,
                )
                .wrapContentSize(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Paddings.Medium),
        ) {
            prefixIcon?.invoke()
            Box(
                modifier = Modifier.weight(Weights.Medium),
                contentAlignment = Alignment.CenterStart,
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium
                        .copy(color = textColor.copy(alpha = Alpha.High)),
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                )
                if (value.isEmpty()) {
                    BodyMediumText(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurface
                            .copy(alpha = Alpha.Medium),
                    )
                }
            }
            suffixIcon?.invoke()
        }
    }
}

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = modifier,
        label = "メールアドレス",
        prefixIcon = {
            Icon(
                icon = Icons.Outlined.Email,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        singleLine = true,
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    enable: Boolean,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = modifier,
        label = "パスワード",
        prefixIcon = {
            Icon(
                icon = if (enable) {
                    Icons.Outlined.Lock
                } else {
                    Icons.Outlined.LockOpen
                },
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        singleLine = true,
    )
}
