package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.views.auth.Login
import com.example.ngebacot.views.auth.Register

class LogResActivity :ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "Login"){
                composable("Login"){
                    Login(navController = navController)
                }
                composable("Register"){
                    Register(navController = navController)
                }
            }
        }
    }
}