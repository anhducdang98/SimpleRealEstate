package com.example.simplerealestate.ui.features.propertylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplerealestate.core.util.Resource
import com.example.simplerealestate.domain.usecase.GetPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val getPropertiesUseCase: GetPropertiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PropertyListUiState>(PropertyListUiState.Loading)
    val uiState: StateFlow<PropertyListUiState> = _uiState.asStateFlow()

    init {
        loadProperties()
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
}