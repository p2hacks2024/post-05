package com.example.flush.domain.use_case

import android.content.Intent
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(resultData: Intent): Result<Unit> =
        withContext(ioDispatcher) {
            try {
                val authResult = authRepository.signInWithGoogle(resultData)
                if (authResult.isSuccess) {
                    if (authResult.getOrNull()?.additionalUserInfo?.isNewUser == true) {
                        val user = User(
                            uid = authResult.getOrNull()?.user?.uid ?: "",
                            email = authResult.getOrNull()?.user?.email ?: "",
                            name = "名無し",
                        )
                        val createUserResult = userRepository.createUser(user)
                        if (createUserResult.isSuccess) {
                            Result.success(Unit)
                        } else {
                            Result.failure(createUserResult.exceptionOrNull() ?: Exception("Register failed"))
                        }
                    } else {
                        Result.success(Unit)
                    }
                } else {
                    Result.failure(authResult.exceptionOrNull() ?: Exception("Register failed"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
