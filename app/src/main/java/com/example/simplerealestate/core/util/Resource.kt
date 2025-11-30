package com.example.simplerealestate.core.util

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}