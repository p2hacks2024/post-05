package com.example.flush.ui.feature.user_settings

import com.example.flush.ui.base.UiEvent

sealed interface UserSettingsUiEvent : UiEvent {
    data object OnNavigateToSearchClick : UserSettingsUiEvent
    data class OnNameInputChange(val name: String) : UserSettingsUiEvent
    data object OnImagePickerLaunchClick : UserSettingsUiEvent
    data object OnSignOutClick : UserSettingsUiEvent
}