package com.example.homework17.network

import com.example.homework17.service.LoginApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LoginClient {
    private const val BASE_URL = "https://reqres.in/api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}

object ApiClient {
    val apiService: LoginApiService by lazy {
        LoginClient.retrofit.create(LoginApiService::class.java)
    }
}

