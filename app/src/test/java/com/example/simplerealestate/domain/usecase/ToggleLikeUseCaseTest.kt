package com.example.simplerealestate.domain.usecase

import com.example.simplerealestate.domain.repository.PropertyRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
class ToggleLikeUseCaseTest {

    private lateinit var repository: PropertyRepository
    private lateinit var useCase: ToggleLikeUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = ToggleLikeUseCase(repository)
    }

    @Test
    fun givenRepositoryReturnsTrue_whenInvoke_thenReturnsTrue() {
        // given
        val propertyId = "1"
        whenever(repository.toggleLike(propertyId)).thenReturn(true)

        // when
        val result = useCase(propertyId)

        // then
        assertTrue(result)
    }

    @Test
    fun givenRepositoryReturnsFalse_whenInvoke_thenReturnsFalse() {
        // given
        val propertyId = "1"
        whenever(repository.toggleLike(propertyId)).thenReturn(false)

        // when
        val result = useCase(propertyId)

        // then
        assertFalse(result)
    }
}