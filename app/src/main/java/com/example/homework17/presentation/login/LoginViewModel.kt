package com.example.homework17.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.App
import com.example.homework17.data.common.Resource
import com.example.homework17.data.datastorepreferance.DataStoreHelper
import com.example.homework17.domain.log_in.LogInRepository
import com.example.homework17.domain.log_in.LogInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val logInRepository: LogInRepository) :
    ViewModel() {
    private val _dataFlow: MutableStateFlow<Resource<LogInResponse>?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    fun loginUser(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {

            logInRepository.logIn(email, password).collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> _dataFlow.value = Resource.Success(data = it.data).also {

                        if (rememberMe) {
                            saveCredentials()
                        }
                    }

                    is Resource.Error -> _dataFlow.value = Resource.Error(it.errorMessage)
                }
            }
        }
    }

    private fun saveCredentials() {
        val authToken = logInRepository.getAuthToken()
        if (!authToken.isNullOrBlank()) {
            viewModelScope.launch {
                DataStoreHelper.saveAuthToken(App.application.applicationContext, authToken)
            }
        }
    }
}





