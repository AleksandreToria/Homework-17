package com.example.homework17.di

import com.example.homework17.data.log_in.LogInRepositoryImpl
import com.example.homework17.data.log_in.LoginApiService
import com.example.homework17.data.registration.RegisterApiService
import com.example.homework17.data.registration.RegisterRepositoryImpl
import com.example.homework17.domain.log_in.LogInRepository
import com.example.homework17.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLogInRepository(loginApiService: LoginApiService): LogInRepository {
        return LogInRepositoryImpl(loginApiService)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerApiService: RegisterApiService): RegisterRepository {
        return RegisterRepositoryImpl(registerApiService)
    }
}