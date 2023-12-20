package com.example.ngebacot.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.ngebacot.testingjangandipake.iconTwitter

@Composable
fun HomePage() {
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