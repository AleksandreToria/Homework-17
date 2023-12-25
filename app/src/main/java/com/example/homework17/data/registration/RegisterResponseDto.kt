package com.example.homework17.data.registration

import com.squareup.moshi.Json

data class RegisterResponseDto(
    @Json(name = "token")
    val token: String
)