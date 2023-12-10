package com.example.ngebacot.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
//    val label: String,
    val icon: ImageVector,
    val filledIcon: ImageVector, // Tambahkan properti filledIcon
    val route: String
)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
//        label = "Home",
        icon = Icons.Outlined.Home,
        filledIcon = Icons.Rounded.Home, // Tambahkan filledIcon
        route = Screens.HomePage.name,
    ),
    NavItem(
//        label = "Profile",
        icon = Icons.Outlined.Person,
        filledIcon = Icons.Rounded.Person, // Tambahkan filledIcon
        route = Screens.ProfilePage.name
    )
)
