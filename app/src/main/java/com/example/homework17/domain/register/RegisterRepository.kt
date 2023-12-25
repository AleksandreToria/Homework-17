package com.example.homework17.domain.register

import com.example.homework17.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(email: String, password: String): Flow<Resource<RegisterResponse>>
}