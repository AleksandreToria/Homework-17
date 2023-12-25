package com.example.homework17.data.registration

import com.example.homework17.dataclass.Data
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("register")
    suspend fun registerUser(@Body registerData: Data): Response<RegisterResponseDto>
}