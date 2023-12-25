package com.example.homework17.data.registration

import com.example.homework17.data.common.Resource
import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.ErrorResponse
import com.example.homework17.domain.register.RegisterRepository
import com.example.homework17.domain.register.RegisterResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerService: RegisterApiService) :
    RegisterRepository {

    override suspend fun register(
        email: String,
        password: String
    ): Flow<Resource<RegisterResponse>> {
        return flow {
            emit(Resource.Loading)
            val moshi: Moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val errorAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)

            try {
                val response = registerService.registerUser(Data(email, password))

                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!.toDomain()))
                } else {
                    val serverErrorData = response.errorBody()?.string()
                    val errorResponse = errorAdapter.fromJson(serverErrorData ?: "")
                    val errorMessage = errorResponse?.error ?: "Unknown error"

                    emit(Resource.Error("Code: ${response.code()}: $errorMessage"))
                }

            } catch (e: IOException) {
                emit(Resource.Error("Network error occurred: $e"))
            } catch (e: Exception) {
                emit(Resource.Error("An unexpected error occurred: $e"))
            }
        }
    }
}