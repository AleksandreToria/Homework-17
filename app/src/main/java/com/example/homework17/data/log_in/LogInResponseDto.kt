package com.example.homework17.data.log_in

import com.squareup.moshi.Json

data class LogInResponseDto(
    @Json(name = "token")
    val token: String
)
