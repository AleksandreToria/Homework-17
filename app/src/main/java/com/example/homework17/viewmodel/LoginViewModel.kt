package com.example.homework17.viewmodel

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.common.Resource
import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.Token
import com.example.homework17.datastorepreferance.DataStoreHelper
import com.example.homework17.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel : ViewModel() {
    private val _dataFlow: MutableStateFlow<Resource<Token>?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val errorAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)

    data class ErrorResponse(val error: String)

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
                    val errorResponse = errorAdapter.fromJson(serverErrorData ?: "")
                    val errorMessage = errorResponse?.error ?: "Unknown error"
                    _dataFlow.value = Resource.Error("Code: ${response.code()}: $errorMessage")
                }
            } catch (e: IOException) {
                _dataFlow.value = Resource.Error("Network error occurred: $e")
            } catch (e: Exception) {
                _dataFlow.value = Resource.Error("An unexpected error occurred: $e")
            }
        }
    }

    private fun saveCredentials(token: String, context: Context) {
        viewModelScope.launch {
            DataStoreHelper.saveAuthToken(context, token)
        }
    }
}



