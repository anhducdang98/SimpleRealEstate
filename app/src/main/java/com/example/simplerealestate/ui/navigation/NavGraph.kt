package com.example.simplerealestate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.simplerealestate.ui.features.propertylist.PropertyListScreen

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = NavRoutes.PROPERTY_LIST
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoutes.PROPERTY_LIST) {
            PropertyListScreen()
        }
    }
}