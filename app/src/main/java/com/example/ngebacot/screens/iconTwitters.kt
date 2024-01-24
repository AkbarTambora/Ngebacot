package com.example.ngebacot.screens

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
import com.example.ngebacot.ui.theme.NgebacotTheme
import com.example.ngebacot.ui.theme.btnColorLogin
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun iconTwitters() {
    var text by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

//    untuk limit karakter yg diketik user (sama kaya twitter max 280 karakter)
    val maxLength = 280
// Mendapatkan token (pastikan Anda telah mengimplementasikan logika autentikasi)
//    disini cari cara untuk ambil token
    val token = "your_token_here"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
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
            Button(
                onClick = {
//                    cari cara untuk mengambil function createPost, karena tidak terbaca. Functionnya ada di PostViewModel
                    /*PostViewModel.createPost(token, postText) */// Gantilah dengan cara memanggil metode createPost yang sesuai
                },
                modifier = Modifier
                    .height(65.dp)
                    .width(90.dp)
                    .padding(top = 30.dp)
                    .offset(x = 280.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(btnColorLogin)
            ) {
                Text("Post", fontSize = 14.sp)
            }
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

