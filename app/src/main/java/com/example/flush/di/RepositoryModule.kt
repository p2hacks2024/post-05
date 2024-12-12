package com.example.flush.di

import com.example.flush.data.repository.AuthRepositoryImpl
import com.example.flush.data.repository.UserRepositoryImpl
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        signInClient: SignInClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): AuthRepository = AuthRepositoryImpl(auth, signInClient, ioDispatcher)

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): UserRepository = UserRepositoryImpl(firestore, ioDispatcher)
}
