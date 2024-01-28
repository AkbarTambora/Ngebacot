package com.example.ngebacot

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.screens.HomePage
import com.example.ngebacot.views.auth.Login
import com.example.ngebacot.views.auth.Register

@Composable
fun LogResActivity() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable(route = "Login") {
            Login(navController = navController)
        }
        composable(route = "Register") {
            Register(navController = navController)
        }
        composable(route = "Homepage") {
            HomePage(navController = navController)
        }
    }
}