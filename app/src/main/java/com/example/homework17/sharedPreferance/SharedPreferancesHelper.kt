package com.example.homework17.sharedPreferance

import android.content.Context

object SharedPreferencesHelper {
    private const val PREF_NAME = "user_prefs"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_AUTH_TOKEN = "auth_token"

    fun saveUserEmail(context: Context, userEmail: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_USER_EMAIL, userEmail).apply()
    }

    fun getUserEmail(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun saveAuthToken(context: Context, authToken: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, authToken).apply()
    }

    fun getAuthToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearUserEmail(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(KEY_USER_EMAIL).apply()
    }

    fun clearAuthToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }
}
