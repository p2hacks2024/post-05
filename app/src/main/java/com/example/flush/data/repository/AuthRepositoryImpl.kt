package com.example.flush.data.repository

import android.content.Intent
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.example.flush.BuildConfig
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.AuthRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val signInClient: SignInClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    private val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build(),
        )
        .setAutoSelectEnabled(true)
        .build()

    override fun getCurrentUser() = auth.currentUser

    override suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser> =
        withContext(ioDispatcher) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                Result.success(result.user!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun requestGoogleOneTapAuth(): IntentSenderRequest =
        withContext(ioDispatcher) {
            try {
                val result = signInClient.beginSignIn(signInRequest).await()
                val pendingIntent = result.pendingIntent
                val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
                intentSenderRequest
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error requesting Google One Tap", e)
                throw e
            }
        }

    override suspend fun signInWithGoogle(resultData: Intent): Result<AuthResult> =
        withContext(ioDispatcher) {
            try {
                val credential = signInClient.getSignInCredentialFromIntent(resultData)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    val result = auth.signInWithCredential(firebaseCredential).await()
                    Result.success(result)
                } else {
                    Log.e("AuthRepository", "Google ID token is null")
                    Result.failure(Exception("Google ID token is null"))
                }
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error signing in with Google One Tap", e)
                Result.failure(Exception("Error signing in with Google One Tap"))
            }
        }
}
