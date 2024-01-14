package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.ngebacot.navigation.AppNavigation
import com.example.ngebacot.views.auth.Login
import com.example.ngebacot.views.auth.Register
import com.example.ngebacot.LogResActivity

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogResActivity()
        }
    }
}