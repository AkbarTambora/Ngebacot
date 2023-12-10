package com.example.ngebacot.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ngebacot.component.BottomBar
import com.example.ngebacot.component.TopBar
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
                    NgebacotApp()
                }
            }
        }
    }
}

@Composable
//fun NgebacotApp(modifier: Modifier = Modifier) {
//    Scaffold(
//        bottomBar = { BottomBar() }
//    ) {
//    }

}
@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(50) { index ->
            Text("Item $index", modifier = Modifier.fillMaxWidth().padding(16.dp))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Home Page"
)
@Composable
fun NgebacotAppPreview() {
    NgebacotTheme {
        NgebacotApp()
        TopBar()
    }
}
