package com.example.ngebacot

import HomePage
import HomeViewModel
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.core.data.remote.client.ApiClient
import com.example.ngebacot.views.auth.Login
import com.example.ngebacot.views.auth.Register

@Composable
fun LogResActivity(context: Context) {
    val navController = rememberNavController()

    // Pass the context if required
    val context = LocalContext.current // Get the current context if needed

    // Create an instance of ApiService using ApiClient
    val apiService = ApiClient(context).apiService

    // Create an instance of HomeViewModel and pass ApiService to it
    val homeViewModel = HomeViewModel(context = context, apiService = apiService)

    NavHost(navController = navController, startDestination = "Login") {
        composable(route = "Login") {
            Login(navController = navController)
        }
        composable(route = "Register") {
            Register(navController = navController)
        }
        composable(route = "Homepage") {
            // Pass homeViewModel to HomePage
            HomePage(navController = navController, homeViewModel = homeViewModel)
        }
    }
}
