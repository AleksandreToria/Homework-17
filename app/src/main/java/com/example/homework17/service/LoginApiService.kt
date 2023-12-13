package com.example.homework17.service

import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun loginUser(@Body loginData: Data): Response<Token>

    @POST("check-token-validity")
    suspend fun checkTokenValidity(@Body token: Token): Response<Unit>
}