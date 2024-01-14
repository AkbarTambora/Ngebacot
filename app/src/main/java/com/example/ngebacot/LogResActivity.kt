package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    }
}