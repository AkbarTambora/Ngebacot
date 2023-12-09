package com.example.ngebacot.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ngebacot.ui.theme.NgebacotTheme


@Composable
fun TopBar() {
    val biruBaseCard = Color(0xFF7C92F5)
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = biruBaseCard,
            ),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)

        ) {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                text = "Welcome Back",
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    NgebacotTheme {
        TopBar()
    }
}