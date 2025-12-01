package com.example.simplerealestate.domain.usecase

import app.cash.turbine.test
import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.domain.repository.PropertyRepository
import com.example.simplerealestate.testutil.PropertyFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
class GetPropertiesUseCaseTest {

    private lateinit var repository: PropertyRepository
    private lateinit var useCase: GetPropertiesUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetPropertiesUseCase(repository)
    }

    @Test
    fun givenRepositoryReturnsSuccess_whenInvoke_thenEmitsLoadingThenSuccessWithProperties() = runTest {
        // given
        val properties = listOf(PropertyFactory.createProperty())
        whenever(repository.getProperties()).thenReturn(Resource.Success(properties))

        // when
        val flow = useCase()

        // then
        flow.test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)

            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertEquals(properties, (success as Resource.Success).data)

            awaitComplete()
        }
    }

    @Test
    fun givenRepositoryReturnsError_whenInvoke_thenEmitsLoadingThenError() = runTest {
        // given
        val errorMessage = "Something went wrong"
        whenever(repository.getProperties()).thenReturn(Resource.Error(errorMessage))

        // when
        val flow = useCase()

        // then
        flow.test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)

            val error = awaitItem()
            assertTrue(error is Resource.Error)
            assertEquals(errorMessage, (error as Resource.Error).message)

            awaitComplete()
        }
    }

    @Test
    fun givenRepositoryReturnsEmptyList_whenInvoke_thenEmitsLoadingThenSuccessWithEmptyList() = runTest {
        // given
        whenever(repository.getProperties()).thenReturn(Resource.Success(emptyList()))

        // when
        val flow = useCase()

        // then
        flow.test {
            val loading = awaitItem()
            assertTrue(loading is Resource.Loading)

            val success = awaitItem()
            assertTrue(success is Resource.Success)
            assertTrue((success as Resource.Success).data.isEmpty())

            awaitComplete()
        }
    }
}