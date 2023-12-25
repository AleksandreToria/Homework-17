package com.example.homework17.data.registration

import com.example.homework17.domain.register.RegisterResponse

fun RegisterResponseDto.toDomain(): RegisterResponse {
    return RegisterResponse(token)
}