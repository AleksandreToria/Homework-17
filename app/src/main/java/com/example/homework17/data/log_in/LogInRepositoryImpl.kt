package com.example.homework17.data.log_in

import com.example.homework17.data.common.Resource
import com.example.homework17.dataclass.Data
import com.example.homework17.dataclass.ErrorResponse
import com.example.homework17.domain.log_in.LogInRepository
import com.example.homework17.domain.log_in.LogInResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(private val logInService: LoginApiService) :
    LogInRepository {
    private var authToken: String? = null

    override suspend fun logIn(email: String, password: String): Flow<Resource<LogInResponse>> {
        return flow {
            emit(Resource.Loading)
            val moshi: Moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val errorAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)

            try {
                val response = logInService.loginUser(Data(email, password))

                if (response.isSuccessful) {
                    authToken = response.body()!!.token ?: ""
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

    override fun getAuthToken(): String? {
        return authToken
    }
}
