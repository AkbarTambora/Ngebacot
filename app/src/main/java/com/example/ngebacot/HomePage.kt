package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.ngebacot.ui.theme.NgebacotTheme

class HomePage : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NgebacotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    NameApp()
                }
            }
        }
    }
}

@Composable
fun NameApp(){
    val baseCardAtas = Color(0xFF7C92F5)

    val poppins = FontFamily (
        Font(R.font.poppins_reguler, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
    Card (
        colors = CardDefaults.cardColors(
            containerColor = baseCardAtas,
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .size(width = 0.dp, height = 50.dp)
            .fillMaxWidth()
    ) {
        Text(
            fontSize = 18.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            text = "Ngebacot",
            modifier = Modifier
                .padding(top = 15.dp)
                .align(alignment = Alignment.CenterHorizontally),
        )
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Home Page"
)

@Composable
fun TittleBar() {
    NgebacotTheme {
        NameApp()
    }
}
