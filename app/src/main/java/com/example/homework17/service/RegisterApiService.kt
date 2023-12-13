package com.example.homework17.service

import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("register")
    suspend fun registerUser(@Body registerData: Data): Response<Token>
}