package com.example.ngebacot.testingjangandipake

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ngebacot.ui.theme.NgebacotTheme


//class iconTwitter : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            NgebacotTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                )
//                {
//                    ComposableWithPencilIcon()
//                }
//            }
//        }
//    }
//}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun iconTwitter() {
    var text by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Transparent)
    ) {
        // TextField untuk menulis teks (ditampilkan atau disembunyikan berdasarkan isEditing)
        if (isEditing) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text("Tulis bacotan anda...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .height(500.dp)
                    .background(Color.Yellow), //(gabisa) Ubah warna dasar dari TextField,
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
                    focusedIndicatorColor = Color.Green, // Ubah warna teks saat fokus
                    cursorColor = Color.Green // Ubah warna kursor
                )
            )
        }

        // Tombol untuk membuka atau menutup mode edit
        IconButton(
            onClick = {
                isEditing = !isEditing

                if (isEditing) {
                    // Fokus ke TextField ketika mode edit diaktifkan
                    focusManager.clearFocus()
                    keyboardController?.show()
                } else {
                    // Sembunyikan keyboard dan fokus dari TextField ketika mode edit dinonaktifkan
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 0.dp, x = 140.dp)
                .background(Color.Transparent)
        ) {
            Icon(
                imageVector =
                if (isEditing) Icons.Default.Send
                else Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposableWithPencilIconPreview() {
    NgebacotTheme {
        iconTwitter()
    }
}

