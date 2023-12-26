package com.example.homework17.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.domain.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {
    private val _dataFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getUserEmail().collect {
                _dataFlow.value = it
            }
        }
    }

    fun clearAuthToken() {
        viewModelScope.launch {
            dataStoreRepository.clearAuthToken()
        }
    }

    fun clearUserEmail() {
        viewModelScope.launch {
            dataStoreRepository.clearUserEmail()
        }
    }
}