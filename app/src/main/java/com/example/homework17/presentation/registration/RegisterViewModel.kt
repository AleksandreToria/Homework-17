package com.example.homework17.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.data.common.Resource
import com.example.homework17.domain.register.RegisterRepository
import com.example.homework17.domain.register.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) :
    ViewModel() {
    private val _dataFlow: MutableStateFlow<Resource<RegisterResponse>?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {

            registerRepository.register(email, password).collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> _dataFlow.value = Resource.Success(data = it.data)
                    is Resource.Error -> _dataFlow.value = Resource.Error(it.errorMessage)
                }
            }
        }
    }
}
