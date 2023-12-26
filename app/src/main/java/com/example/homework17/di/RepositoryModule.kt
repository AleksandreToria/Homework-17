package com.example.homework17.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.homework17.data.datastore.DataStoreRepositoryImpl
import com.example.homework17.data.log_in.LogInRepositoryImpl
import com.example.homework17.data.log_in.LoginApiService
import com.example.homework17.data.registration.RegisterApiService
import com.example.homework17.data.registration.RegisterRepositoryImpl
import com.example.homework17.domain.datastore.DataStoreRepository
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

    @Singleton
    @Provides
    fun provideDatastoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }
}