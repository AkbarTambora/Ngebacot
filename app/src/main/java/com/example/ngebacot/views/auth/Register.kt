@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class
)

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.R
import com.example.ngebacot.core.data.remote.client.ApiService
import com.example.ngebacot.core.data.remote.request.RegisterRequest
import com.example.ngebacot.core.utils.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Serializable
class UserRegister() {
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
}

class ErrorMessage() {
    var emailErrorText by mutableStateOf("")// Pesan kesalahan untuk inputan email
    var usernameErrorText by mutableStateOf("")// Pesan kesalahan untuk inputan username
    var passwordErrorText by mutableStateOf("")// Pesan kesalahan untuk inputan password
}


@OptIn(ExperimentalMaterial3Api::class)
sealed class RegisterResult {
    data class Error(val errorMessage: String) : RegisterResult()
}


fun emailRegex(email: String, errorMessage: ErrorMessage): String {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    if (!email.matches(emailRegex)) {
        // Set pesan kesalahan untuk email invalid format
        errorMessage.emailErrorText = "Invalid email format"
    }
    return errorMessage.emailErrorText
}

fun usernameRegex(username: String, errorMessage: ErrorMessage): String {
    val usernameRegex = Regex("^[^\\s]{1,20}$")
    if (!username.matches(usernameRegex)) {
        // Set pesan kesalahan untuk email invalid format
        errorMessage.usernameErrorText  = "Username contains no space"
    }
    return errorMessage.usernameErrorText
}

fun passwordRegex(password: String, errorMessage: ErrorMessage): String {
    val passwordRegex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d\\s])[a-zA-Z\\d[^a-zA-Z\\d\\s]]{8,30}$")
    if (!password.matches(passwordRegex)) {
        // Set pesan kesalahan untuk email invalid format
        errorMessage.passwordErrorText  = "Min 8 character" +
                "Combination Uppercase and Lower Case, " +
                "Number, and Symbol"
    }
    return errorMessage.passwordErrorText
}

suspend fun onClickRegister(email: String, username: String, password: String, errorMessage: ErrorMessage,navController: NavHostController) {

    val baseUrl = AppConstants.BASE_URL

    // Tandai sedang registrasi
    isRegistering = true

    // Reset pesan kesalahan
    noInternetMessage = ""
    statusCodeMessage = ""
    generalErrorMessage = ""

    // Cek koneksi internet
    checkInternetConnection()

//    val emailCheck = emailRegex(email, errorMessage)
//    val usernameCheck = usernameRegex(username, errorMessage)
//    val passwordCheck = passwordRegex(password, errorMessage)

    val registerRequest = RegisterRequest(email, username, password)

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)


    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch {
        if (isInternetAvailable) {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.register(registerRequest)
                }

                emailRegex(email, errorMessage)
                usernameRegex(username, errorMessage)
                passwordRegex(password, errorMessage)

                if (response.isSuccessful) {
                    // Register berhasil
                    val authResponse = response.body()
                    println("Registration successful. Auth token: ${authResponse?.jwtToken}")
                    // Arahkan pengguna ke halaman login setelah berhasil registrasi
                    withContext(Dispatchers.Main) {
                        isRegistering = false // Hentikan animasi loading
                        navController.navigate("Login")
                    }

                } else {
                    // Error occurred during registration
                    println("Registration failed. Status code: ${response.code()}")
                    println("Response body: ${response.errorBody()?.string()}")

                    when (response.code()) {
                        400 -> {
                            // Tangani Status Code 400
                            val errorBody = response.errorBody()?.string()
                            val errorJson = JSONObject(errorBody)
                            val errors = errorJson.getJSONObject("errors")

                            // Ambil pesan kesalahan untuk setiap field
                            val usernameError = errors.getJSONArray("username").getString(0)
                            val emailError = errors.getJSONArray("email").getString(0)
                            val passwordError = errors.getJSONArray("password"). getString(0)

                            // Set pesan kesalahan ke dalam errorMessage
                            errorMessage.usernameErrorText = usernameError
                            errorMessage.emailErrorText = emailError
                            errorMessage.passwordErrorText = passwordError
                        }
                        401 -> RegisterResult.Error("Unauthorized")
                        403 -> RegisterResult.Error("Forbidden")
                        500 -> {
                            // Tampilkan pesan status code 500
                            statusCodeMessage = "Server Error"
                            // ...
                        }
                        else -> {
                            // Tangani status code lainnya jika diperlukan
                            errorMessage.usernameErrorText = "Error occurred. Please try again."
                            errorMessage.emailErrorText = "Error occurred. Please try again."
                            errorMessage.passwordErrorText = "Error occurred. Please try again."
                        }
                    }
                    // Handle status code lainnya jika diperlukan
                }
            } catch (e: Exception) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
//                println("Error: ${e.message}")
                generalErrorMessage = "No Internet Connection"
            } finally {
                // Setelah selesai registrasi, hentikan animasi loading
                isRegistering = false
            }
        } else{
            // Tampilkan pesan ketika tidak ada koneksi internet
            noInternetMessage = "No internet connection"
            // Hentikan animasi loading
            isRegistering = false
        }
    }
}


// Tambahkan state untuk menyimpan status koneksi internet
var isInternetAvailable by mutableStateOf(true)

// Tambahkan state untuk menampilkan animasi loading saat registrasi
var isRegistering by mutableStateOf(false)

// Tambahkan state untuk menyimpan pesan status code 500
var statusCodeMessage by mutableStateOf("")

// Tambahkan state untuk menampilkan pesan ketika tidak ada koneksi internet
var noInternetMessage by mutableStateOf("")

// Tambahkan state untuk menampilkan pesan kesalahan umum
var generalErrorMessage by mutableStateOf("")

// Fungsi untuk mengecek koneksi internet
fun checkInternetConnection() {
    // Implementasi untuk mengecek koneksi internet
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    navController: NavHostController = rememberNavController()
) {

    // Tambahkan pesan untuk menampilkan loading ketika registrasi
    if (isRegistering) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    // Tambahkan pesan untuk menampilkan dialog ketika tidak ada koneksi internet
    if (!isInternetAvailable) {
        AlertDialog(
            onDismissRequest = { /* Tidak melakukan apa-apa ketika dialog ditutup */ },
            title = { Text(text = "No Internet Connection") },
            text = { Text(text = "Please check your internet connection and try again") },
            confirmButton = {
                Button(
                    onClick = {
                        // Tutup dialog
                        isInternetAvailable = true
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }

    // Tambahkan pesan untuk menampilkan dialog ketika mendapatkan status code 500
    if (statusCodeMessage.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { /* Tidak melakukan apa-apa ketika dialog ditutup */ },
            title = { Text(text = "Server Error") },
            text = { Text(text = statusCodeMessage) },
            confirmButton = {
                Button(
                    onClick = {
                        // Tutup dialog
                        statusCodeMessage = ""
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }

    // Tambahkan pesan kesalahan umum
    if (generalErrorMessage.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { /* Tidak melakukan apa-apa ketika dialog ditutup */ },
            title = { Text(text = "Error") },
            text = { Text(text = generalErrorMessage) },
            confirmButton = {
                Button(
                    onClick = {
                        // Tutup dialog
                        generalErrorMessage = ""
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }

//
//    data class UserRegister(
//        val email: String,
//        val username: String,
//        val password: String,
//        val confirmPassword: String
//    )

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

    val userRegister = remember {UserRegister()}
//    var email by remember { mutableStateOf("")}
//    var username by remember { mutableStateOf("")}
//    var password by remember { mutableStateOf("")}
//    var confirmPassword by remember { mutableStateOf("")}

//    validasi password, email,username
    val passwordMismatch = userRegister.password.isNotEmpty() && userRegister.password != userRegister.confirmPassword
    val errorText = if (passwordMismatch) "Password doesn't match" else ""
    val errorMessage = remember {ErrorMessage()}

//    LaunchedEffect(passwordMismatch) {
//        if (passwordMismatch) {
//            // Tunggu sebentar sebelum menampilkan pesan kesalahan
//            delay(100)
//            focusManager.clearFocus()
//        }
//    }

    /*
    *   Untuk memperbaiki error Compossable invocations can only happen from the
    * context of a @composable function
    */
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
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
                value = userRegister.email,
                onValueChange = { email ->
                    userRegister.email = email
                    errorMessage.emailErrorText = ""
                },
                label = { Text("Email") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                    .clickable { focusManager.clearFocus() }
                ,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = outlineInputColor, // Warna outline saat input fokus
                    unfocusedBorderColor = outlineInputColor, // Warna outline saat input tidak fokus
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus

                ),
                shape = RoundedCornerShape(50.dp),
            )
            // Tampilkan pesan kesalahan email di bawah inputan email
            if (errorMessage.emailErrorText.isNotEmpty()) {
                Text(
                    text = errorMessage.emailErrorText,
                    color = Color.Red,
                    fontSize = 12.sp, // Ukuran font yang lebih kecil
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp, bottom = 4.dp) // Geser ke kiri dan tambahkan margin bawah
                )
            }
//            Username
            OutlinedTextField(
                value = userRegister.username,
                onValueChange = { newText ->
                    userRegister.username = newText
                    errorMessage.usernameErrorText = ""
                },
                label = { Text("Username") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                    .clickable { focusManager.clearFocus() }
                ,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = outlineInputColor, // Warna outline saat input fokus
                    unfocusedBorderColor = outlineInputColor, // Warna outline saat input tidak fokus
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus

                ),
                shape = RoundedCornerShape(50.dp),

            )
            // Tampilkan pesan kesalahan username di bawah inputan username
            if (errorMessage.usernameErrorText.isNotEmpty()) {
                Text(
                    text = errorMessage.usernameErrorText,
                    color = Color.Red,
                    fontSize = 12.sp, // Ukuran font yang lebih kecil
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 4.dp) // Geser ke kiri dan tambahkan margin bawah
                )
            }
//          Password
            OutlinedTextField(
                value = userRegister.password,
                onValueChange = { newText -> userRegister.password = newText },
                label = { Text("Password") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                    .clickable { focusManager.clearFocus() }
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

            // Tampilkan pesan kesalahan password di bawah inputan password
            if (errorMessage.passwordErrorText.isNotEmpty()) {
                Text(
                    text = errorMessage.passwordErrorText,
                    color = Color.Red,
                    fontSize = 12.sp, // Ukuran font yang lebih kecil
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 4.dp) // Geser ke kiri dan tambahkan margin bawah
                )
            }

//            Confirm Password
            OutlinedTextField(
                value = userRegister.confirmPassword,
                onValueChange = { newText -> userRegister.confirmPassword = newText },
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .height(75.dp)
                    .width(270.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 15.dp)
                    .clickable { focusManager.clearFocus() }
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
                    fontSize = 12.sp, // Ukuran font yang lebih kecil
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp, bottom = 4.dp) // Geser ke kiri dan tambahkan margin bawah
                )
            }
            Row(modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                onClickRegister(
                                    userRegister.email,
                                    userRegister.username,
                                    userRegister.password,
                                    errorMessage,
                                    navController
                                )
                            }
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .width(270.dp)
                            .padding(top = 10.dp)
                            /*
                            Modifier .conditional
                            jika emailRegex, usernameRegex, dan passwordRegex
                            isNotEmpty, maka navigate to login
                             */
                            .clickable {
                                navController.navigate("Login")
                            }
                        ,
                        enabled = !passwordMismatch,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(btnColorLogin)
                    ) {
                        Text("Register",
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Medium
                        )
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

@Composable
fun NavigateToLogin() {
    Login()
}