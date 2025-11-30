package com.example.simplerealestate.data.repository

import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.data.mapper.toDomain
import com.example.simplerealestate.data.remote.api.PropertyApiService
import com.example.simplerealestate.domain.model.Property
import com.example.simplerealestate.domain.repository.PropertyRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Singleton
class PropertyRepositoryImpl @Inject constructor(
    private val apiService: PropertyApiService
) : PropertyRepository {

    override suspend fun getProperties(): Resource<List<Property>> {
        return try {
            val response = apiService.getProperties()
            val properties = response.results.toDomain()
            Resource.Success(properties)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unexpected error occurred")
        }
    }
}