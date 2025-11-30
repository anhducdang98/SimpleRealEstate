package com.example.simplerealestate.ui.features.propertylist

import androidx.compose.runtime.Stable
import com.example.simplerealestate.domain.model.Property

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Stable
sealed class PropertyListUiState {
    data object Loading : PropertyListUiState()
    data class Success(val properties: List<Property>) : PropertyListUiState()
    data object Error : PropertyListUiState()
}