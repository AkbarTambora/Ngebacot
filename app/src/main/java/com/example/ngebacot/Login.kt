package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val biruBaseCard = Color(0xFF7C92F5)
    val btnColorLogin = Color(0xFFFF6978)
    val outlineInputColor = Color(0xFFFFFFFF)
    var text by remember { mutableStateOf("")

    }
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = biruBaseCard,
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 320.dp)
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
                    .align(alignment = Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Username") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                ,
                shape = RoundedCornerShape(50.dp),
            )
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Password") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                ,
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(outlineInputColor)
            )
            Button(onClick = { onClick() },
                modifier = Modifier
                    .height(48.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
//                    .padding(0.dp,10.dp,0.dp,0.dp)
                  ,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(btnColorLogin)
            ) {
                Text("Login")
            }
            Text(
                text = "Don’t have an account? Sign Up!",
                modifier = Modifier
//                    .padding(6.dp)
//                    .align(alignment = Alignment.CenterHorizontally)
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