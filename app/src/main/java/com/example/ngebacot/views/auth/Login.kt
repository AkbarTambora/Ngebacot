package com.example.ngebacot.views.auth

//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.R
import com.example.ngebacot.core.data.remote.client.ApiClient
import com.example.ngebacot.core.data.remote.client.ApiService
import com.example.ngebacot.core.data.remote.request.LoginRequest
import com.example.ngebacot.core.domain.model.AuthModel
import com.example.ngebacot.core.domain.model.UserModel
import com.example.ngebacot.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
class UserLogin() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    hasError: Boolean = false,
    navController: NavHostController = rememberNavController()
) {
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
//    var username by remember { mutableStateOf("")}
//    var password by remember { mutableStateOf("")}

//    kode untuk hide or not pw
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }

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
                    Button(onClick = {
                        coroutineScope.launch{
                            onClickLogin(userLogin.username, userLogin.password)
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


/*
*  Handle api data login, dont forget to see http response and code thankyou
*/
@OptIn(ExperimentalMaterial3Api::class)
suspend fun onClickLogin(username: String, password: String) {
    val loginRequest = LoginRequest(username, password)
    val apiService = ApiClient.apiService

    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch {
        try {
            val response = apiService.login(loginRequest)

            /*
            *   Condition handle isSuccess Login
            */

            // Check if the API call was successful
            if (response.isSuccessful) {
                // Parse the AuthResponse from the Api response body
                val authResponse = response.body()

                // Check if the AuthResponse is not null
                if (authResponse != null) {
                    // Extract JWT token and user detail from AuthResponse
                    val jwtToken = authResponse.jwtToken
                    val user = authResponse.user

                    // Simpan token JWT ke model autentikasi (jika diperlukan)
                    val authModel = AuthModel(jwtToken, user)

                    // Access user detailsbhjkf tyi rf,tjftmhjfmkf
                    val userId = user.id
                    val username = user.username
                    val email = user.email
                    val name = user.name
                    val coverpic = user.coverpic
                    val profilepic = user.profilepic
                    val city = user.city
                    val website = user.website
                    val created_at = user.created_at
                    UserModel(userId, username,email,name,coverpic,profilepic,city,website,created_at)
                    // NavController needed to fix
                    //navController.navigate(Screens.HomePage.name)
                    Screens.HomePage
                }

            } else {
                // Tangani status code yang tidak berhasil
                when (response.code()){
                    401 -> {

                    }
                    403 -> {

                    }
                    500 ->{

                    }
                    else -> {

                    }
                }

            }
        } catch (e: Exception) {
            // Tangani kesalahan jaringan atau kesalahan lainnya
            // Tampilkan pesan kesalahan sesuai dengan kebutuhan
            e.printStackTrace()

        }
    }


}