package com.example.homework17.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.data.dataclass.Token
import com.example.homework17.data.log_in.LoginApiService
import com.example.homework17.domain.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel
@Inject constructor(
    private val logInService: LoginApiService,
    private val dataStoreRepository: DataStoreRepository
) :
    ViewModel() {

    private val _dataFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val dataFlow = _dataFlow.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getAuthToken().collect {
                _dataFlow.value = it
            }
        }
    }

    suspend fun checkTokenValidity(token: String): Boolean {
        return try {
            val response = logInService.checkTokenValidity(Token(token))
            response.isSuccessful
        } catch (e: IOException) {
            false
        } catch (e: HttpException) {
            false
        }
    }
}
