package com.example.ngebacot.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.ngebacot.core.data.local.auth.AuthLocalDatastore
import com.example.ngebacot.core.data.remote.client.ApiClient
import com.example.ngebacot.core.data.remote.client.ApiService
import com.example.ngebacot.ui.theme.NgebacotTheme
import com.example.ngebacot.ui.theme.btnColorLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.example.ngebacot.core.domain.model.PostModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun iconTwitters(
    context: Context = LocalContext.current,
) {
    var text by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

//    variable handle api
    val apiClient = remember { ApiClient(context = context) }
    val coroutineScope = rememberCoroutineScope()
    val jwtToken = AuthLocalDatastore.getToken(context) ?: ""
    val user = AuthLocalDatastore.getUser(context)
    val userId = user?.id ?: -1L
    val content = PostModel(caption = text, img = "url_placeholder")

//    untuk limit karakter yg diketik user (sama kaya twitter max 280 karakter)
    val maxLength = 280
// Mendapatkan token (pastikan Anda telah mengimplementasikan logika autentikasi)
//    disini cari cara untuk ambil token
    val token = "your_token_here"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .zIndex(1f)
    ) {
        // TextField untuk menulis teks (ditampilkan atau disembunyikan berdasarkan isEditing)
        if (isEditing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(239, 230, 221)),
            )
//            input text
            TextField(
                value = text,
                onValueChange = {
                    if (it.length <= maxLength)
                        text = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                label = { Text("Tulis bacotan anda...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .padding(top = 65.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                ,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Aksi yang dijalankan ketika keyboard "Done" ditekan
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(11,10,10),// Ubah warna teks saat fokus
                    cursorColor = Color(11,10,10), // Ubah warna kursor
                    containerColor = Color(239,230,221),
//                    textColor = Color(11,10,10),
                )
            )
//            button untuk post postingan
            PostButton(apiClient.apiService, coroutineScope, jwtToken, userId, content)

            IconButton(
                onClick = {
                    isEditing = !isEditing
                    if (!isEditing) {
                        // Sembunyikan keyboard dan fokus dari TextField ketika mode edit dinonaktifkan
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                },
                modifier = Modifier
                    .background(Color.Transparent)
                    .align(Alignment.TopStart)
                    .padding(start = 22.dp, top = 21.dp)
                    .zIndex(1f)
            ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(32.dp)
                )
            }
        } else {
            IconButton(
                onClick = {
                    isEditing = !isEditing
                    if (isEditing) {
                        // Fokus ke TextField ketika mode edit diaktifkan
                        focusManager.clearFocus()
                        keyboardController?.show()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.Transparent)
                    .padding(end = 22.dp, bottom = 22.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
}

fun onClick(text: String) {
    println("Clicked with text: $text")

}

@Preview(showBackground = true)
@Composable
fun ComposableWithPencilIconPreview() {
    NgebacotTheme {
        iconTwitters()
    }
}

@Composable
// button post
fun PostButton(
    apiService: ApiService,
    coroutineScope: CoroutineScope,
    jwtToken: String,
    userId: Number, // ID pengguna terkait dengan konten
    content: PostModel // Data konten yang ingin diposting
    ){
    Button(
        onClick = {
            coroutineScope.launch {
                postContent(
                    apiService,
                    jwtToken,
                    userId,
                    content
                )
            }
        },
        modifier = Modifier
            .height(65.dp)
            .width(90.dp)
            .padding(top = 30.dp)
            .offset(x = 280.dp)
            .zIndex(1f),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(btnColorLogin)
    ) {
        Text("Post", fontSize = 14.sp)
    }
}

suspend fun postContent(
    apiService: ApiService,
    jwtToken: String,
    userId: Number, // ID pengguna terkait dengan konten
    content: PostModel // Data konten yang ingin diposting
) {
    val headers = mapOf(
        "Authorization" to "Bearer $jwtToken",
        "Content-Type" to "application/json"
    )

    try {
        // Melakukan panggilan POST ke API untuk memposting konten
        val postResponse = apiService.postContent(headers, userId, content)

        // Memeriksa apakah responsenya berhasil
        if (postResponse.isSuccessful) {
            println("Post successful")
            // Handle jika posting berhasil
        } else {
            // Handle jika posting tidak berhasil
            val errorBody = postResponse.errorBody()?.string()
            println("Error: ${postResponse.code()}, Body: $errorBody")
        }
    } catch (e: Exception) {
        // Handle kesalahan eksekusi atau jaringan saat posting
        println("Error: ${e.message}")
        e.printStackTrace()
    }
}


