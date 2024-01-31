@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.ngebacot.screens

import PostModel
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.ngebacot.core.domain.model.UserModel
import com.example.ngebacot.ui.theme.NgebacotTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class PostingStatus {
    var isPosting by mutableStateOf(false)
    var isEditing by mutableStateOf(false)
    var isEditingInitialized by mutableStateOf(false) // New property
    var isPostSuccess by mutableStateOf(false)
}

fun generatePostId(): Int {
    val uuid = UUID.randomUUID()
    return uuid.hashCode()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun IconTwitters(
    onPostSuccess: () -> Unit = {},
    context: Context = LocalContext.current,
    img: String? = null
) {
    var text by remember { mutableStateOf("") }
    val postingStatus = remember { PostingStatus() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    // Handling Api
    val apiClient = remember { ApiClient(context = context) }
    val coroutineScope = rememberCoroutineScope()
    val jwtToken = AuthLocalDatastore.getToken(context) ?: ""
    val user = AuthLocalDatastore.getUser(context)
    val userId = user?.id ?: -1
    val currentTime = LocalDateTime.now().toString()
    val content = PostModel(
        id = generatePostId(),
        caption = text,
        img = img,
        userId = userId,
        created_at = currentTime,
        user = user ?: UserModel(
            id = -1,
            email = "",
            username = "",
            name = "",
            city = "",
            profilepic = "",
            coverpic = "",
            created_at = "",
            website = ""
        )
    )

    val maxLength = 280

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .zIndex(1f)
    ) {
        if (postingStatus.isEditing) {
            // Reset text to empty when entering editing mode
            if (!postingStatus.isEditingInitialized) {
                text = ""
                postingStatus.isEditingInitialized = true
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(239, 230, 221)),
            )
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
                    .padding(top = 65.dp, start = 20.dp, end = 20.dp, bottom = 0.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(11, 10, 10),
                    cursorColor = Color(11, 10, 10),
                    containerColor = Color(239, 230, 221),
                )
            )
            PostButton(
                apiClient.apiService,
                coroutineScope,
                jwtToken,
                userId,
                content,
                postingStatus,
                onPostSuccess = {
                    postingStatus.isPosting = false
                    postingStatus.isPostSuccess = true
                    onPostSuccess()
                    // Reset text to empty after successful post
                    text = ""
                }
            )
            IconButton(
                onClick = {
                    postingStatus.isEditing = !postingStatus.isEditing
                    if (!postingStatus.isEditing) {
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
                    postingStatus.isEditing = !postingStatus.isEditing
                    if (postingStatus.isEditing) {
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

@Composable
fun MainContent() {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()

        // Use LaunchedEffect to trigger the Snackbar when isPostSuccess changes
        LaunchedEffect(snackbarHostState) {
            if (snackbarHostState.currentSnackbarData != null) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Post successful")
                }
            }
        }

        IconTwitters(
            onPostSuccess = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Post successful")
                }
            }
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PostButton(
    apiService: ApiService,
    coroutineScope: CoroutineScope,
    jwtToken: String,
    userId: Number,
    content: PostModel,
    postingStatus: PostingStatus,
    onPostSuccess: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (!postingStatus.isPosting && !isLoading) {
                postingStatus.isPosting = true
                isLoading = true
                coroutineScope.launch {
                    try {
                        postContent(
                            apiService,
                            jwtToken,
                            userId,
                            content
                        )
                        onPostSuccess()
                    } catch (e: Exception) {
                        println("Error posting: ${e.message}")
                    } finally {
                        postingStatus.isPosting = false
                        isLoading = false
                    }
                }
            }
        },
        modifier = Modifier
            .height(65.dp)
            .width(90.dp)
            .padding(top = 30.dp)
            .offset(x = 280.dp)
            .zIndex(1f),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(Color.Blue)
    ) {
        Text("Post", fontSize = 14.sp)
    }
}

suspend fun postContent(
    apiService: ApiService,
    jwtToken: String,
    userId: Number,
    content: PostModel
) {
    val headers = mapOf(
        "Authorization" to "Bearer $jwtToken",
        "Content-Type" to "application/json"
    )

    try {
        val postResponse = apiService.postContent(headers, userId, content)

        if (postResponse.isSuccessful) {
            println("Post successful")
        } else {
            val errorBody = postResponse.errorBody()?.string()
            println("Error: ${postResponse.code()}, Body: $errorBody")
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun IconTwittersPreview() {
    NgebacotTheme {
        IconTwitters()
        MainContent()
    }
}
