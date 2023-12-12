package com.example.ngebacot.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ngebacot.R
import com.example.ngebacot.ui.theme.NgebacotTheme

class BacotanUser  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgebacotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                )
                {
                    BacotanUser(Message("Android", "Compose", "Jetpack Compose"))
                }
            }
        }
    }
}

data class Message(val nama: String, val username: String,  val body: String)

@Composable
fun BacotanUser(msg: Message) {
        OutlinedCard (
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 7.dp, 15.dp, 7.dp)
        ) {
            Row (
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.androidparty),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        // Set image size to 40 dp
                        .size(58.dp)
                        // Clip image to be shaped as a circle
                        .clip(CircleShape)
                )
                // Add a horizontal space between the image and the column
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = msg.nama,
                        fontWeight = FontWeight.Bold
                        )
                    Text(text = msg.username)
                    // Add a vertical space between the author and message texts
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = msg.body)
                }
            }
        }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Dashboard"
)
@Composable
fun PreviewMessageCard() {
    NgebacotTheme {
        Column {
            BacotanUser(
                msg = Message("Lexi", "@akbar", "Hey, take a look at Jetpack Compose, it's great!")
            )
            BacotanUser(
                msg = Message("Jono", "@kur","Hey, take a look at Jetpack Compose, it's great!")
            )
        }

    }
}

