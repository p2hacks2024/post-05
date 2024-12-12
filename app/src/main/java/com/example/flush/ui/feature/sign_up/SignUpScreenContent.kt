package com.example.flush.ui.feature.sign_up

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.flush.R
import com.example.flush.ui.compose.BodyMediumText
import com.example.flush.ui.compose.EmailTextField
import com.example.flush.ui.compose.FilledButton
import com.example.flush.ui.compose.Image
import com.example.flush.ui.compose.OutlinedButton
import com.example.flush.ui.compose.PasswordTextField
import com.example.flush.ui.compose.TitleLargeText
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.BorderStrokeWidth
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.EmailTextFieldHeight
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.GoogleSignUpButtonHeight
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.HorizontalDividerWidth
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.MinPasswordLength
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.PasswordTextFieldHeight
import com.example.flush.ui.feature.sign_up.SignUpScreenDimensions.SubmitButtonHeight
import com.example.flush.ui.theme.dimensions.Paddings

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    onEvent: (SignUpUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = Paddings.Large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.Large),
    ) {
        SignUpHeader()
        EmailSignUpFields(
            uiState = uiState,
            onEvent = onEvent,
        )
        HorizontalDivider(
            modifier = Modifier
                .width(HorizontalDividerWidth)
                .padding(vertical = Paddings.Large),
        )
        GoogleSignUpButton(
            onClick = { onEvent(SignUpUiEvent.OnGoogleSignUpClick) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun SignUpHeader(
    modifier: Modifier = Modifier,
) {
    TitleLargeText(
        text = "サインアップ",
        modifier = modifier
            .padding(Paddings.Medium),
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
private fun EmailSignUpFields(
    uiState: SignUpUiState,
    onEvent: (SignUpUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.ExtraLarge),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Paddings.Large),
        ) {
            EmailTextField(
                email = uiState.email,
                onEmailChange = { onEvent(SignUpUiEvent.OnEmailInputChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(EmailTextFieldHeight),
            )
            PasswordTextField(
                password = uiState.password,
                onPasswordChange = { onEvent(SignUpUiEvent.OnPasswordInputChange(it)) },
                enable = uiState.password.length >= MinPasswordLength,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PasswordTextFieldHeight),
            )
        }
        SubmitButton(
            onSubmit = { onEvent(SignUpUiEvent.OnSubmitClick) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.email.isNotEmpty() && uiState.password.length >= MinPasswordLength,
        )
    }
}

@Composable
private fun SubmitButton(
    onSubmit: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    FilledButton(
        text = "新規登録",
        onClick = onSubmit,
        modifier = modifier
            .height(SubmitButtonHeight),
        enabled = enabled,
    )
}

@Composable
private fun GoogleSignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(GoogleSignUpButtonHeight),
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(
            horizontal = Paddings.Medium,
            vertical = Paddings.Small,
        ),
        border = BorderStroke(width = BorderStrokeWidth, MaterialTheme.colorScheme.onSurface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                resId = R.drawable.ic_google,
                modifier = Modifier.align(Alignment.CenterStart),
            )
            BodyMediumText(
                text = "Googleで新規登録",
            )
        }
    }
}
