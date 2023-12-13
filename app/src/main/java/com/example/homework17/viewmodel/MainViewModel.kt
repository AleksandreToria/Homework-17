package com.example.homework17.viewmodel

import androidx.lifecycle.ViewModel
import com.example.homework17.dataclass.Token
import com.example.homework17.network.ApiClient
import retrofit2.HttpException
import java.io.IOException

object MainViewModel : ViewModel() {

    suspend fun checkTokenValidity(token: String): Boolean {
        return try {
            val response = ApiClient.apiService.checkTokenValidity(Token(token))
            response.isSuccessful
        } catch (e: IOException) {
            false
        } catch (e: HttpException) {
            false
        }
    }
}
