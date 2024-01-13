package com.example.ngebacot.views.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.ngebacot.R
import com.example.ngebacot.core.data.remote.client.ApiService
import com.example.ngebacot.core.utils.AppConstants
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay


//data class UserRegister(
//    val email: String,
//    val username: String,
//    val password: String,
//    val confirmPassword: String
//)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    navController: NavHostController
) {

//    confirm password
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    val showPassword2 = remember { mutableStateOf(false) }

//    warna
    val biruBaseCard = Color(0xFF7C92F5)
    val btnColorLogin = Color(0xFFFF6978)
    val outlineInputColor = Color(0xFFFFFFFF)

//    custom font
    val poppins = FontFamily (
        androidx.compose.ui.text.font.Font(R.font.poppins_reguler, FontWeight.Normal),
        androidx.compose.ui.text.font.Font(R.font.poppins_medium, FontWeight.Medium),
        androidx.compose.ui.text.font.Font(R.font.poppins_semibold, FontWeight.SemiBold),
        androidx.compose.ui.text.font.Font(R.font.poppins_bold, FontWeight.Bold)
    )

    val logoBacot = FontFamily(
        androidx.compose.ui.text.font.Font(R.font.adlamdisplay_reguler, FontWeight.Normal)
    )

    var email by remember { mutableStateOf("")}
    var username by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var confirmPassword by remember { mutableStateOf("")}

//    validasi password
    val passwordMismatch = password.isNotEmpty() && password != confirmPassword
    val errorText = if (passwordMismatch) "Password doesn't match" else ""

    LaunchedEffect(passwordMismatch) {
        if (passwordMismatch) {
            // Tunggu sebentar sebelum menampilkan pesan kesalahan
            delay(100)
            focusManager.clearFocus()
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = biruBaseCard,
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 535.dp)
                .align(Alignment.Center)
        ) {
            Text(
                fontSize = 18.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                text = "Let's Join Us!",
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Text(
                fontSize = 32.sp,
                fontFamily = logoBacot,
                color = Color(245, 245, 245),
                text = "Ngebacot",
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
//            Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
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
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus

                ),
                shape = RoundedCornerShape(50.dp),


            )
//            Username
            OutlinedTextField(
                value = username,
                onValueChange = { newText -> username = newText },
                label = { Text("Username") },
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

                ),
                shape = RoundedCornerShape(50.dp),
            )
//          Password
            OutlinedTextField(
                value = password,
                onValueChange = { newText -> password = newText },
                label = { Text("Password") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                ,
                singleLine = true,
//                isError = hasError || matchError.value,
                visualTransformation =
                if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val (icon, iconColor) = if (showPassword.value) {
                        Pair(
                            Icons.Filled.Visibility,
                            colorResource(id = R.color.teal_200)
                        )
                    } else {
                        Pair(Icons.Filled.VisibilityOff, colorResource(id = R.color.white))
                    }

                    IconButton(onClick = { showPassword.value = !showPassword.value }) {
                        Icon(
                            icon,
                            contentDescription = "Visibility",
                            tint = iconColor
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = outlineInputColor, // Warna outline saat input fokus
                    unfocusedBorderColor = outlineInputColor, // Warna outline saat input tidak fokus
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus

                ),
                shape = RoundedCornerShape(50.dp),
            )
//            Confirm Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newText -> confirmPassword = newText },
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                ,
                isError = passwordMismatch
                ,
                singleLine = true,
                visualTransformation =
                if (showPassword2.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val (icon, iconColor) = if (showPassword2.value) {
                        Pair(
                            Icons.Filled.Visibility,
                            colorResource(id = R.color.teal_200)
                        )
                    } else {
                        Pair(Icons.Filled.VisibilityOff, colorResource(id = R.color.white))
                    }

                    IconButton(onClick = { showPassword2.value = !showPassword2.value }) {
                        Icon(
                            icon,
                            contentDescription = "Visibility",
                            tint = iconColor
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = outlineInputColor, // Warna outline saat input fokus
                    unfocusedBorderColor = outlineInputColor, // Warna outline saat input tidak fokus
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus

                ),
                shape = RoundedCornerShape(50.dp),
            )
            if (passwordMismatch) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding( start = 90.dp)
                )
            }
            Row(modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { onClickRegister(email, username, password, confirmPassword) },
                        modifier = Modifier
                            .height(60.dp)
                            .width(270.dp)
                            .padding(top = 10.dp)
                        ,
                        enabled = !passwordMismatch,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(btnColorLogin)
                    ) {
                        Text("Register", fontSize = 20.sp, fontFamily = poppins, fontWeight = FontWeight.Medium)
                    }
                    Row {
                        Text(
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            text = "Already have an account? ",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                        )
                        Text(
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            textDecoration = Underline,
                            color = Color.White,
                            text = "Log In!",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable {
                                    navController.navigate("Login")
                                }
                        )
                    }
                }
            }
        }
    }

}

suspend fun onClickRegister(email: String, username: String, password: String, confirmPassword: String) {
    val baseUrl = AppConstants.BASE_URL
    val registerEndpoint = "api/register" // Sesuaikan dengan endpoint registrasi di server

    val emailValue = email
    val usernameValue = username
    val passwordValue = password

    // Pastikan bahwa password dan konfirmasi password sesuai
    val registerData = RegisterRequest(emailValue, usernameValue, passwordValue)

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    try {
        val response = apiService.register(registerData)

        if (response.isSuccessful) {
            // Registrasi berhasil
            println("Registrasi berhasil")
        } else {
            // Terjadi kesalahan saat registrasi
            println("Registrasi gagal. Status code: ${response.code()}")
            println("Response body: ${response.errorBody()?.string()}")
        }
    } catch (e: Exception) {
        // Tangani kesalahan koneksi atau kesalahan lainnya
        println("Error: ${e.message}")
    }
}