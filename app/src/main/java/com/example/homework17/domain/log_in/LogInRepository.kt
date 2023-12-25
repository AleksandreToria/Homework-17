package com.example.homework17.domain.log_in

import com.example.homework17.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logIn(email: String, password: String): Flow<Resource<LogInResponse>>
    fun getAuthToken(): String?
}