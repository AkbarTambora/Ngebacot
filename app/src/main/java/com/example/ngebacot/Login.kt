package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ngebacot.ui.theme.NgebacotTheme


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgebacotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    BaseCard()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseCard() {
    val abu = Color(0xFFa1a1a1)
    var text by remember { mutableStateOf("") }
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = abu,
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 460.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Welcome Back",
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Text(
                text = "Ngebacot",
                modifier = Modifier
//                    .padding(6.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Username") }
            )
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Password") }
            )
            Button(onClick = { onClick() }) {
                Text("Login")
            }
            Text(
                text = "Ngebacot",
                modifier = Modifier
                    .padding(6.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }

}

fun onClick() {

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Dashboard"
)
@Composable
fun GreetingPreview() {
    NgebacotTheme {
        BaseCard()
    }
}