package com.example.ngebacot.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ngebacot.R
import com.example.ngebacot.ui.theme.hitamgaitam
import java.time.temporal.TemporalQueries.offset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(hasError: Boolean = false){

    var text by remember { mutableStateOf("Akbar bar") }
    var  name by remember {mutableStateOf("Oy oy") }
    var username by remember {mutableStateOf("Username")}
    var email by remember {mutableStateOf("Email")}
    var password by remember {mutableStateOf("Password")}
    val icon = Icons.Rounded.RemoveRedEye

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        val showPassword = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ){
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Edit profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    modifier = Modifier
                        .padding(top = 13.dp)
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .offset( x = 190.dp)
//                        .background( Color(0xFF111111)),
                    ,
                    colors = ButtonDefaults.buttonColors(hitamgaitam)
                ) {
                    Text(
                        text = "Save",
                    )
                }
            }
//            foto profile
            Image(
                painter = painterResource(id = R.drawable.foto_profil),
                contentDescription = stringResource(id = R.string.txt_profile),
                contentScale = ContentScale.Fit ,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp)
                    .background(Color.White)
                    .border(2.dp, Color.Black, shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .offset(x = 83.dp, y = -30.dp)
//                    .background(Color.Red)
                    .width(30.dp)
                    .height(30.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(50.dp))
//                    .clip(shape = RoundedCornerShape(25.dp))
            ){
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .size(26.dp)
                            .rotate(-7F)

                    )
                }
            }

//            Name
            Text(
                text = "Nama",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 3.dp)
            )
            BasicTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(top = 3.dp)
                )
//            Username
            Text(
                text = "Username",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 3.dp, top =  23.dp)
            )
            BasicTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(top = 3.dp)
            )

//            Email
            Text(
                text = "Username",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 3.dp, top =  23.dp)
            )
            BasicTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(top = 3.dp)
            )
//            Password
            Text(
                text = "Username",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 3.dp, top =  23.dp)
            )
            BasicTextField(
                value = password,
                onValueChange = { password = it},
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                )
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(top = 2.dp)
            )
            IconButton(
                onClick = { showPassword.value = !showPassword.value },
                modifier = Modifier
                    .offset(y = -40.dp, x = 315.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = "Visibility",
//                        tint = iconColor
                )
            }
        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
//    name = "Dashboard"
)
@Composable
fun ProfilePagePreview(){
    ProfilePage()
}