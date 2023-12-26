package com.example.homework17.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.homework17.domain.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl
@Inject constructor(private val dataStore: DataStore<Preferences>) : DataStoreRepository {

    private val emailKey = stringPreferencesKey("user_email")
    private val authKey = stringPreferencesKey("auth_token")

    override suspend fun saveUserEmail(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[emailKey] = userEmail
        }
    }

    override fun getUserEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[emailKey]
        }
    }

    override suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[authKey] = authToken
        }
    }

    override fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[authKey]
        }
    }

    override suspend fun clearUserEmail() {
        dataStore.edit { preferences ->
            preferences.remove(emailKey)
        }
    }

    override suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(authKey)
        }
    }
}