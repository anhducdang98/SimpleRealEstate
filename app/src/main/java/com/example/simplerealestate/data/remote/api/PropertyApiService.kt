package com.example.simplerealestate.data.remote.api

import com.example.simplerealestate.data.model.PropertiesResponseDto
import retrofit2.http.GET

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
interface PropertyApiService {

    @GET("properties")
    suspend fun getProperties(): PropertiesResponseDto
}