package com.example.homework17.datastorepreferance

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_datastore")

object DataStoreHelper {

    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun saveUserEmail(context: Context, userEmail: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = userEmail
        }
    }

    fun getUserEmail(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_EMAIL_KEY]
        }
    }

    suspend fun saveAuthToken(context: Context, authToken: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = authToken
        }
    }

    fun getAuthToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }
    }

    suspend fun clearUserEmail(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_EMAIL_KEY)
        }
    }

    suspend fun clearAuthToken(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }
}


