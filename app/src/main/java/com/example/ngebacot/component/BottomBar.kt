package com.example.ngebacot.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ngebacot.R
import com.example.ngebacot.model.BottomBarItem
import com.example.ngebacot.ui.theme.NgebacotTheme

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF7C92F5)){
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        backgroundColor
    ) {
        val bottomNavigation = listOf(
            BottomBarItem(
                title = stringResource(id = R.string.txt_home),
                icon = Icons.Default.Home
            ), BottomBarItem(
                title = stringResource(id = R.string.txt_profile),
                icon = Icons.Default.Person
            )
        )
        bottomNavigation.map {
            NavigationBarItem(
                selected = it.title == bottomNavigation[0].title,
                onClick = { },
                icon = {
                    val iconModifier = if (it.title == bottomNavigation[0].title) {
                        Modifier
                            .size(32.dp)
//                            .clip(CircleShape) // Menghilangkan latar belakang shape oval
                    } else {
                        Modifier // Atur modifikasi default untuk ikon yang tidak aktif
                    }

                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title,
                        modifier = iconModifier,
                        tint = if (it.title == bottomNavigation[0].title) {
                            Color.Black // Atur warna putih untuk ikon yang aktif
                        } else {
                            MaterialTheme.colorScheme.primary // Atur warna default untuk ikon yang tidak aktif
                        }
                    )
                },
                label = { Text(text = it.title) })
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    NgebacotTheme {
        BottomBar()
    }
}