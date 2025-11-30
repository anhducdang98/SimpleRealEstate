package com.example.simplerealestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.simplerealestate.ui.navigation.NavGraph
import com.example.simplerealestate.ui.theme.SimpleRealEstateTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleRealEstateTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}