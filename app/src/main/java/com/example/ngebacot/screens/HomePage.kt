package com.example.ngebacot.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ngebacot.testingjangandipake.iconTwitter

@Composable
fun HomePage(navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "HomePage",
            fontFamily = FontFamily.Serif,
            fontSize = 22.sp)
    }
    iconTwitter()
}