package com.example.ngebacot.views.auth

//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.R
import com.example.ngebacot.core.data.local.auth.AuthLocalDatastore
import com.example.ngebacot.core.data.remote.client.ApiClient
import com.example.ngebacot.core.data.remote.client.ApiService
import com.example.ngebacot.core.data.remote.request.LoginRequest
import com.example.ngebacot.core.domain.model.UserModel
import com.example.ngebacot.core.utils.checkInternetConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

@Serializable
class UserLogin() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
}

@OptIn(ExperimentalMaterial3Api::class)
sealed class LoginResult {
    data class Success(val userModel: UserModel) : LoginResult()
    data class Error(val errorMessage: String) : LoginResult()
}

//class ErrorMessage() {
//    var usernameErrorText by mutableStateOf("") // Pesan kesalahan untuk inputan username
//    var passwordErrorText by mutableStateOf("") // Pesan kesalahan untuk inputan password
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    hasError: Boolean = false,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController()
) {
    val apiClient = remember { ApiClient(context = context) }
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

    val userLogin = remember { UserLogin() }
    val coroutineScope = rememberCoroutineScope()
    val errorMessage = remember {ErrorMessage()}
    val context = LocalContext.current // Ambil context lokal
//    var username by remember { mutableStateOf("")}
//    var password by remember { mutableStateOf("")}

//    kode untuk hide or not pw
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    // State untuk status koneksi internet
    val isInternetConnected = remember { mutableStateOf(checkInternetConnection(context)) }

    // Menggunakan effect untuk memperbarui status koneksi internet
    DisposableEffect(isInternetConnected.value) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isInternetConnected.value = true
            }

            override fun onLost(network: Network) {
                isInternetConnected.value = false
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        // Hapus callback saat komponen dihentikan
        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = biruBaseCard,
            ),
            modifier = Modifier
                .size(width = 340.dp, height = 380.dp)
                .align(Alignment.Center)
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
                fontFamily = logoBacot,
                color = Color(245, 245, 245),
                text = "Ngebacot",
                modifier = Modifier
                    .padding(bottom = 7.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = userLogin.username,
                onValueChange = { userLogin.username = it },
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
                    focusedLabelColor = outlineInputColor, // Warna label saat input fokus

                ),
                shape = RoundedCornerShape(50.dp),


                )
            OutlinedTextField(
                value = userLogin.password,
                onValueChange = { newText -> userLogin.password = newText },
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

                ),
                shape = RoundedCornerShape(50.dp),
//                kode tambahan untuk hide or nor pw
                singleLine = true,
                isError = hasError,
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val (icon, iconColor) = if (showPassword.value) {
                        Pair(
                            Icons.Filled.Visibility,
                            colorResource(id = R.color.white)
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
            )
            Row(modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)) {
                Column() {
                    // Pengecekan koneksi internet sebelum login
                    if (!isInternetConnected.value) {
                        AlertDialog(
                            onDismissRequest = { /* Tidak lakukan apa-apa saat dialog ditutup */ },
                            title = { Text(text = "No Internet Connection") },
                            text = {
                                Text(
                                    text = "Please check your internet connection and try again.",
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = { /* Tidak lakukan apa-apa saat tombol "OK" ditekan */ },
                                    colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    // Menggunakan instance apiClient untuk mengakses apiService
                    LoginButton(
                        apiClient.apiService,
                        userLogin,
                        coroutineScope,
                        context,
                        navController,
                        hasError,
                        errorMessage
                    )
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
                            text = "Register",
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable {
                                    navController.navigate("Register")
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginButton(apiService: ApiService,userLogin: UserLogin, coroutineScope: CoroutineScope, context: Context, navController: NavHostController,hasError:Boolean, errorMessage: ErrorMessage){
    val btnColorLogin = Color(0xFFFF6978)
//    custom font
    val poppins = FontFamily (
        androidx.compose.ui.text.font.Font(R.font.poppins_reguler, FontWeight.Normal),
        androidx.compose.ui.text.font.Font(R.font.poppins_medium, FontWeight.Medium),
        androidx.compose.ui.text.font.Font(R.font.poppins_semibold, FontWeight.SemiBold),
        androidx.compose.ui.text.font.Font(R.font.poppins_bold, FontWeight.Bold)
    )

    Button(
        onClick = {
            coroutineScope.launch {
                onClickLogin(
                    apiService,
                    userLogin.username,
                    userLogin.password,
                    context,
                    navController,
                    hasError,
                    errorMessage
                )
            }
        },
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
}

/*
*  Handle api data login, dont forget to see http response and code thankyou
*/
@OptIn(ExperimentalMaterial3Api::class)
suspend fun onClickLogin(
    apiService: ApiService,
    username: String,
    password: String,
    context: Context,
    navController: NavHostController,
    hasError: Boolean,
    errorMessage: ErrorMessage
) {
    val loginRequest = LoginRequest(username, password)
    val apiClient = ApiClient(context)

    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch {
        try {
            val response = withContext(Dispatchers.IO) {
                apiClient.apiService.login(loginRequest)
            }

            if (response.isSuccessful) {
                val authResponse = response.body()
                println("AuthResponse: $authResponse")
                if (authResponse != null) {
                    val jwtToken = authResponse.jwtToken
                    val user = authResponse.user

                    if (!jwtToken.isNullOrEmpty() && user != null) {
                        AuthLocalDatastore.saveToken(context, jwtToken)
                        AuthLocalDatastore.saveUser(context, user)
                        withContext(Dispatchers.Main) {
                            navController.navigate("HomePage")
                        }
                        navController.navigate("HomePage")
                    } else {
                        println("Error: Unexpected null or empty jwtToken in response")
                        // Handle the case where jwtToken is null or empty
                    }
                } else {
                    // Print error message from the server if available
                    val errorBody = response.errorBody()?.string()
                    println("Error: Unexpected null response body")
                    // Handle null response body
                }
            } else {
                // Print error message from the server if available
                val errorBody = response.errorBody()?.string()
                println("Error: ${response.code()}, Body: $errorBody")
                // Handle unsuccessful response
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            // Handle exceptions
        }
    }
}

