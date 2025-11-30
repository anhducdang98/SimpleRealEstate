package com.example.simplerealestate.domain.usecase

import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.domain.model.Property
import com.example.simplerealestate.domain.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
class GetPropertiesUseCase @Inject constructor(
    private val repository: PropertyRepository
) {
    operator fun invoke(): Flow<Resource<List<Property>>> = flow {
        emit(Resource.Loading)
        emit(repository.getProperties())
    }
}