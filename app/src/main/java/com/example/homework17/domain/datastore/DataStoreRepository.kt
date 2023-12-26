package com.example.homework17.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveUserEmail(userEmail: String)

    fun getUserEmail(): Flow<String?>

    suspend fun saveAuthToken(authToken: String)

    fun getAuthToken(): Flow<String?>

    suspend fun clearUserEmail()

    suspend fun clearAuthToken()
}