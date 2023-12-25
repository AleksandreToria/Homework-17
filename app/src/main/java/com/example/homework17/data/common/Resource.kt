package com.example.homework17.data.common

sealed class Resource<out D> {
    data class Success<out D>(val data: D?) : Resource<D>()
    data object Loading : Resource<Nothing>()
    data class Error<out D>(val errorMessage: String) : Resource<D>()
}
