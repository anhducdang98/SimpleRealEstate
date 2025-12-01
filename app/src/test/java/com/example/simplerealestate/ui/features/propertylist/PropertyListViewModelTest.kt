package com.example.simplerealestate.ui.features.propertylist

import app.cash.turbine.test
import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.domain.usecase.GetPropertiesUseCase
import com.example.simplerealestate.domain.usecase.ToggleLikeUseCase
import com.example.simplerealestate.testutil.PropertyFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
@OptIn(ExperimentalCoroutinesApi::class)
class PropertyListViewModelTest {

    private lateinit var getPropertiesUseCase: GetPropertiesUseCase
    private lateinit var toggleLikeUseCase: ToggleLikeUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPropertiesUseCase = mock()
        toggleLikeUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenNothing_whenViewModelCreated_thenUiStateIsInitial() = runTest {
        // given
        whenever(getPropertiesUseCase()).thenReturn(flow { })

        // when
        val viewModel = PropertyListViewModel(getPropertiesUseCase, toggleLikeUseCase)

        // then
        assertEquals(PropertyListUiState.Initial, viewModel.uiState.value)
    }

    @Test
    fun givenUseCaseReturnsSuccess_whenLoadProperties_thenUiStateChangesFromInitialToLoadingToSuccess() = runTest {
        // given
        val properties = listOf(PropertyFactory.createProperty())
        whenever(getPropertiesUseCase()).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Success(properties))
        })

        // when
        val viewModel = PropertyListViewModel(getPropertiesUseCase, toggleLikeUseCase)

        // then
        viewModel.uiState.test {
            assertEquals(PropertyListUiState.Initial, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(PropertyListUiState.Loading, awaitItem())

            val successState = awaitItem()
            assertTrue(successState is PropertyListUiState.Success)
            assertEquals(properties, (successState as PropertyListUiState.Success).properties)
        }
    }

    @Test
    fun givenUseCaseReturnsError_whenLoadProperties_thenUiStateChangesFromInitialToLoadingToError() = runTest {
        // given
        whenever(getPropertiesUseCase()).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Error("Something went wrong"))
        })

        // when
        val viewModel = PropertyListViewModel(getPropertiesUseCase, toggleLikeUseCase)

        // then
        viewModel.uiState.test {
            assertEquals(PropertyListUiState.Initial, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(PropertyListUiState.Loading, awaitItem())

            val errorState = awaitItem()
            assertTrue(errorState is PropertyListUiState.Error)
        }
    }

    @Test
    fun givenSuccessState_whenToggleLike_thenPropertyLikedStateUpdated() = runTest {
        // given
        val propertyId = "1"
        val property = PropertyFactory.createProperty(id = propertyId, isLiked = false)
        whenever(getPropertiesUseCase()).thenReturn(flow {
            emit(Resource.Success(listOf(property)))
        })
        whenever(toggleLikeUseCase(propertyId)).thenReturn(true)

        val viewModel = PropertyListViewModel(getPropertiesUseCase, toggleLikeUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // when
        viewModel.onEvent(PropertyListEvent.ToggleLike(propertyId))

        // then
        val state = viewModel.uiState.value
        assertTrue(state is PropertyListUiState.Success)
        val updatedProperty = (state as PropertyListUiState.Success).properties.first()
        assertTrue(updatedProperty.isLiked)
    }
}