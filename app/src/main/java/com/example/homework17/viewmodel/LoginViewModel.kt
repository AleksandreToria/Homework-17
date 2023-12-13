package com.example.homework17.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.common.Resource
import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.Token
import com.example.homework17.network.ApiClient
import com.example.homework17.sharedPreferance.SharedPreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel : ViewModel() {
    private val _dataFlow: MutableStateFlow<Resource<Token>?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    fun loginUser(email: String, password: String, context: Context, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.loginUser(Data(email, password))
                if (response.isSuccessful) {
                    val authToken = response.body()?.token ?: ""
                    _dataFlow.value = Resource.Success(response.body()!!)
                    if (rememberMe) {
                        saveCredentials(authToken, context)
                    }
                } else {
                    val serverErrorData = response.errorBody()?.string()
                    _dataFlow.value = Resource.Error("Code: ${response.code()}: $serverErrorData")
                }
            } catch (e: IOException) {
                _dataFlow.value = Resource.Error("Network error occurred: $e")
            } catch (e: Exception) {
                _dataFlow.value = Resource.Error("An unexpected error occurred: $e")
            }
        }
    }


    private fun saveCredentials(token: String, context: Context) {
        SharedPreferencesHelper.saveAuthToken(context, token)
    }
}


