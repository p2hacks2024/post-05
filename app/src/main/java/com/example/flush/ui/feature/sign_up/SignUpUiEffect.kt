package com.example.flush.ui.feature.sign_up

import com.example.flush.ui.base.UiEffect

interface SignUpUiEffect : UiEffect {
    data class ShowToast(val message: String) : SignUpUiEffect
    data object NavigateToSearch : SignUpUiEffect
}
