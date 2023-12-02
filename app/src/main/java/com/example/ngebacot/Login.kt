package com.example.ngebacot

import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
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

//    custom font
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

//    val poppinsBold = GoogleFont("poppins_bold")

    val poppins = FontFamily (
        androidx.compose.ui.text.font.Font(R.font.poppins_reguler, FontWeight.Normal),
        androidx.compose.ui.text.font.Font(R.font.poppins_medium, FontWeight.Medium),
        androidx.compose.ui.text.font.Font(R.font.poppins_semibold, FontWeight.SemiBold),
        androidx.compose.ui.text.font.Font(R.font.poppins_bold, FontWeight.Bold)
    )

    var text by remember { mutableStateOf("")}
    var text2 by remember { mutableStateOf("")}
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = biruBaseCard,
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 380.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                fontSize = 18.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                text = "Welcome Back",
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Text(
                fontSize = 32.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                text = "Ngebacot",
                modifier = Modifier
                    .padding(bottom = 7.dp)
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
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = outlineInputColor, // Warna outline saat input fokus
                    unfocusedBorderColor = outlineInputColor, // Warna outline saat input tidak fokus
                    focusedLabelColor = Color.White, // Warna label saat input fokus
                    cursorColor = Color.Black, // Warna kursor
                    textColor = outlineInputColor, // Warna teks
//                    leadingIconColor = Color.Black, // Warna ikon di sebelah kiri input
//                    trailingIconColor = Color.Black, // Warna ikon di sebelah kanan input
                    errorCursorColor = Color.Red, // Warna kursor saat ada kesalahan
                    errorLabelColor = Color.Red, // Warna label saat ada kesalahan
                    errorBorderColor = Color.Red, // Warna outline saat ada kesalahan
                    errorLeadingIconColor = Color.Red, // Warna ikon di sebelah kiri saat ada kesalahan
                    errorTrailingIconColor = Color.Red, // Warna ikon di sebelah kanan saat ada kesalahan
//                    borderWidth = 2.dp // Ketebalan outline
                ),
                shape = RoundedCornerShape(50.dp),


            )
            OutlinedTextField(
                value = text2,
                onValueChange = { newText -> text2 = newText },
                label = { Text("Password") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                ,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = outlineInputColor, // Warna outline saat input fokus
                    unfocusedBorderColor = outlineInputColor, // Warna outline saat input tidak fokus
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus
                    cursorColor = Color.Black, // Warna kursor
                    textColor = outlineInputColor, // Warna teks
//                    leadingIconColor = Color.Black, // Warna ikon di sebelah kiri input
//                    trailingIconColor = Color.Black, // Warna ikon di sebelah kanan input
                    errorCursorColor = Color.Red, // Warna kursor saat ada kesalahan
                    errorLabelColor = Color.Red, // Warna label saat ada kesalahan
                    errorBorderColor = Color.Red, // Warna outline saat ada kesalahan
                    errorLeadingIconColor = Color.Red, // Warna ikon di sebelah kiri saat ada kesalahan
                    errorTrailingIconColor = Color.Red, // Warna ikon di sebelah kanan saat ada kesalahan
//                    borderWidth = 2.dp // Ketebalan outline
                ),
                shape = RoundedCornerShape(50.dp),
            )
            Row(modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)) {
                Column() {
                    Button(onClick = { onClick() },
                        modifier = Modifier
                            .height(60.dp)
                            .width(270.dp)
                            .padding(top = 15.dp)
                        ,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(btnColorLogin)
                    ) {
                        Text("Login", fontSize = 20.sp, fontFamily = poppins, fontWeight = FontWeight.Medium)
                    }
                    Row {
                        Text(
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            text = "Don’t have an account? ",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                        )
                        Text(
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            textDecoration = Underline,
                            color = Color.White,
                            text = "Sign Up!",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable {  }
                        )
                    }
                }
            }
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