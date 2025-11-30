package com.example.simplerealestate.di

import com.example.simplerealestate.core.constants.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.simplerealestate.data.remote.api.PropertyApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(NetworkConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(NetworkConstants.CONTENT_TYPE_JSON.toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providePropertyApiService(retrofit: Retrofit): PropertyApiService {
        return retrofit.create(PropertyApiService::class.java)
    }
}