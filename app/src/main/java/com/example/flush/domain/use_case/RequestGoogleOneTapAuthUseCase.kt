package com.example.flush.domain.use_case

import androidx.activity.result.IntentSenderRequest
import com.example.flush.domain.repository.AuthRepository
import javax.inject.Inject

class RequestGoogleOneTapAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): IntentSenderRequest = authRepository.requestGoogleOneTapAuth()
}
