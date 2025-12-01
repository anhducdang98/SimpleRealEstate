package com.example.simplerealestate.domain.usecase

import com.example.simplerealestate.domain.repository.PropertyRepository
import javax.inject.Inject

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
class ToggleLikeUseCase @Inject constructor(
    private val repository: PropertyRepository
) {
    operator fun invoke(propertyId: String): Boolean {
        return repository.toggleLike(propertyId)
    }
}