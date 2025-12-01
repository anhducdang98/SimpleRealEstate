package com.example.simplerealestate.domain.repository

import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.domain.model.Property

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
interface PropertyRepository {
    suspend fun getProperties(): Resource<List<Property>>
    fun toggleLike(propertyId: String): Boolean
}