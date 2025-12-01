package com.example.simplerealestate.ui.features.propertylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.domain.usecase.GetPropertiesUseCase
import com.example.simplerealestate.domain.usecase.ToggleLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PropertyListUiState>(PropertyListUiState.Initial)
    val uiState: StateFlow<PropertyListUiState> = _uiState.asStateFlow()

    init {
        loadProperties()
    }

    fun onEvent(event: PropertyListEvent) {
        when (event) {
            is PropertyListEvent.ToggleLike -> toggleLike(event.propertyId)
        }
    }

    fun loadProperties() {
        getPropertiesUseCase()
            .onEach { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> PropertyListUiState.Loading
                    is Resource.Success -> PropertyListUiState.Success(result.data)
                    is Resource.Error -> PropertyListUiState.Error
                }
            }
            .launchIn(viewModelScope)
    }

    private fun toggleLike(propertyId: String) {
        val isNowLiked = toggleLikeUseCase(propertyId)
        _uiState.update { currentState ->
            if (currentState is PropertyListUiState.Success) {
                val updatedProperties = currentState.properties.map { property ->
                    if (property.id == propertyId) {
                        property.copy(isLiked = isNowLiked)
                    } else {
                        property
                    }
                }
                PropertyListUiState.Success(updatedProperties)
            } else {
                currentState
            }
        }
    }
}