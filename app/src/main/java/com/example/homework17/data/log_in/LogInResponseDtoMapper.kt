package com.example.homework17.data.log_in

import com.example.homework17.domain.log_in.LogInResponse

fun LogInResponseDto.toDomain(): LogInResponse {
    return LogInResponse(token)
}