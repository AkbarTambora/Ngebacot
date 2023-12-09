package com.example.ngebacot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun NgebacotApp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
//        your code compose here
        BottomBar()
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
