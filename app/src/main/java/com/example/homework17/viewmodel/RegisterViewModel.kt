package com.example.homework17.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.common.Resource
import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.Token
import com.example.homework17.network.RegisterApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException


class RegisterViewModel : ViewModel() {
    private val _dataFlow: MutableStateFlow<Resource<Token>?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RegisterApiClient.apiService.registerUser(Data(email, password))
                if (response.isSuccessful) {
                    _dataFlow.value = Resource.Success(response.body()!!)
                } else {
                    val serverErrorData = response.errorBody()?.string()
                    _dataFlow.value = Resource.Error("code: ${response.code()}: $serverErrorData")
                }
            } catch (e: IOException) {
                _dataFlow.value = Resource.Error("Network error occurred: $e")
            } catch (e: Exception) {
                _dataFlow.value = Resource.Error("An unexpected error occurred: $e")
            }
        }
    }
}