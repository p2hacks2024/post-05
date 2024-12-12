package com.example.flush.ui.feature.sign_up

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.flush.ui.compose.IconButton
import com.example.flush.ui.compose.TopBar
import com.example.flush.ui.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("ModifierMissing")
@Composable
fun SignUpScreen(
    navigateToAuthSelection: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val latestNavigateToAuthSelection by rememberUpdatedState(navigateToAuthSelection)
    val latestNavigateToSearch by rememberUpdatedState(navigateToSearch)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            viewModel.handleSignInResult(result.data)
        },
    )

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is SignUpUiEvent.OnNavigateToAuthSelectionClick -> latestNavigateToAuthSelection()
                is SignUpUiEvent.OnEmailInputChange -> viewModel.updateEmail(event.email)
                is SignUpUiEvent.OnPasswordInputChange -> viewModel.updatePassword(event.password)
                is SignUpUiEvent.OnSubmitClick -> viewModel.submitRegister()
                is SignUpUiEvent.OnGoogleSignUpClick -> viewModel.startGoogleSignIn(googleSignInLauncher)
            }
        }.launchIn(this)
    }

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEffect.flowWithLifecycle(lifecycleOwner.lifecycle).onEach { effect ->
            when (effect) {
                is SignUpUiEffect.NavigateToSearch -> latestNavigateToSearch()
                is SignUpUiEffect.ShowToast -> showToast(context, effect.message)
            }
        }.launchIn(this)
    }

    SignUpScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    onEvent: (SignUpUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SignUpScreenTopBar(
                onNavigateToAuthSelection = { onEvent(SignUpUiEvent.OnNavigateToAuthSelectionClick) },
            )
        },
    ) { innerPadding ->
        SignUpScreenContent(
            uiState = uiState,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

@Composable
private fun SignUpScreenTopBar(
    onNavigateToAuthSelection: () -> Unit,
) {
    TopBar(
        title = null,
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                icon = Icons.Outlined.ArrowBackIosNew,
                onClick = onNavigateToAuthSelection,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        },
    )
}
