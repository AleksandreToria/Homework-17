package com.example.homework17.presentation.main

import androidx.lifecycle.ViewModel
import com.example.homework17.data.log_in.LoginApiService
import com.example.homework17.dataclass.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel
@Inject constructor(private val logInService: LoginApiService) :
    ViewModel() {

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
