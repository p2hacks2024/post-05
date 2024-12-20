package com.example.flush.ui.feature.user_settings

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.flush.domain.model.User
import com.example.flush.domain.use_case.GetCurrentUserUseCase
import com.example.flush.domain.use_case.SaveUserUseCase
import com.example.flush.domain.use_case.SignOutUseCase
import com.example.flush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : BaseViewModel<UserSettingsUiState, UserSettingsUiEvent, UserSettingsUiEffect>(
    UserSettingsUiState(),
) {
    private var initialUser: User? = null

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .collect { user ->
                    updateUiState { it.copy(name = user.name, imageUri = user.iconUrl) }
                    initialUser = user
                }
        }
    }

    fun updateName(name: String) {
        updateUiState { it.copy(name = name) }
    }

    fun updateImageUri(imageUri: Uri) {
        updateUiState { it.copy(imageUri = imageUri.toString()) }
    }

    fun saveUser() {
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true) }
            val name = uiState.value.name
            val imageUri = uiState.value.imageUri
            val result = initialUser?.let { user ->
                saveUserUseCase(
                    user.copy(
                        name = name,
                        iconUrl = imageUri,
                    ),
                )
            }
            updateUiState { it.copy(isLoading = false) }
            if (result?.isSuccess == true) {
                sendEffect(UserSettingsUiEffect.NavigateToSearch)
            } else {
                sendEffect(UserSettingsUiEffect.ShowToast("Failed to update user"))
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val result = signOutUseCase()
            if (result.isSuccess) {
                sendEffect(UserSettingsUiEffect.NavigateToAuthSelection)
            } else {
                sendEffect(UserSettingsUiEffect.ShowToast("Failed to sign out"))
            }
        }
    }
}
