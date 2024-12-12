package com.example.flush.domain.repository

import com.example.flush.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User): Result<Unit>
}
