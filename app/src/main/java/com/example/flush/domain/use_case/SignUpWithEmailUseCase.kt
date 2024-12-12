package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val authResult = authRepository.signUpWithEmail(email, password)
                if (authResult.isSuccess) {
                    val firebaseUser = authResult.getOrNull()
                    if (firebaseUser != null) {
                        val user = User(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email ?: "",
                            name = "名無し",
                        )
                        val createUserResult = userRepository.createUser(user)
                        if (createUserResult.isSuccess) {
                            Result.success(Unit)
                        } else {
                            Result.failure(createUserResult.exceptionOrNull() ?: Exception("Register failed"))
                        }
                    } else {
                        Result.failure(Exception("User is null"))
                    }
                } else {
                    Result.failure(authResult.exceptionOrNull() ?: Exception("Register failed"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
