package com.example.simplerealestate.ui.features.propertylist

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
sealed class PropertyListEvent {
    data class ToggleLike(val propertyId: String) : PropertyListEvent()
}