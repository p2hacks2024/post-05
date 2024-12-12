package com.example.flush.domain.repository

import android.content.Intent
import androidx.activity.result.IntentSenderRequest
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser>
    suspend fun requestGoogleOneTapAuth(): IntentSenderRequest
    suspend fun signInWithGoogle(resultData: Intent): Result<AuthResult>
}
