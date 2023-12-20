package com.example.ngebacot.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.ngebacot.testingjangandipake.iconTwitter

@Composable
fun HomePage() {
//    Box(modifier = Modifier
//        .fillMaxSize(),
//        contentAlignment = Alignment.Center) {
//        Text(text = "HomePage",
//            fontFamily = FontFamily.Serif,
//            fontSize = 22.sp)
//    }
    iconTwitter()
    Column {
        BacotanUser(
            msg = Message("Jono", "@kur","Hey, take a look at Jetpack Compose, it's great!")
        )
        BacotanUser(
            msg = Message("Akbar", "@kur","Hey, take a look at Jetpack Compose, it's great!")
        )
    }

}