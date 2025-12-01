package com.example.simplerealestate.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Singleton
class PropertiesPreferences @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun getLikedPropertyIds(): Set<String> {
        return prefs.getStringSet(KEY_LIKED_PROPERTIES, emptySet()) ?: emptySet()
    }

    fun toggleLike(propertyId: String): Boolean {
        val currentLiked = getLikedPropertyIds().toMutableSet()
        val isNowLiked = if (currentLiked.contains(propertyId)) {
            currentLiked.remove(propertyId)
            false
        } else {
            currentLiked.add(propertyId)
            true
        }
        prefs.edit { putStringSet(KEY_LIKED_PROPERTIES, currentLiked) }
        return isNowLiked
    }

    companion object {
        private const val PREFS_NAME = "properties_prefs"
        private const val KEY_LIKED_PROPERTIES = "liked_property_ids"
    }
}