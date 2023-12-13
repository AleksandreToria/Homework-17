package com.example.homework17.common


sealed class Resource<D>(
    val data: D? = null,
    val errorMessage: String? = null,
) {
    class Success<D>(data: D) : Resource<D>(data = data)
    class Error<D>(errorMessage: String) : Resource<D>(errorMessage = errorMessage)
}




