package com.example.flush.ui.feature.sign_up

import com.example.flush.ui.base.UiState

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
) : UiState
