package com.example.ngebacot.navigation

import HomePage
import HomeViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.core.data.remote.client.ApiClient
import com.example.ngebacot.screens.IconTwitters
import com.example.ngebacot.screens.ProfilePage
<<<<<<< HEAD
import com.example.ngebacot.screens.IconTwitters
=======
>>>>>>> 6846f8cc43fb859a063e1b3f936891dafd581892

@ExperimentalMaterial3Api
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Pass the context if required
    val context = LocalContext.current // Get the current context if needed

    // Create an instance of ApiService using ApiClient
    val apiService = ApiClient(context).apiService

    // Create an instance of HomeViewModel and pass ApiService to it
    val homeViewModel = HomeViewModel(context = context, apiService = apiService)

    Scaffold(
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.HomePage.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screens.HomePage.name) {
                    // Memasukkan iconTwitter() di dalam HomePage
<<<<<<< HEAD
                    HomePage(navController)
=======
                    HomePage(navController, homeViewModel = homeViewModel)
>>>>>>> 6846f8cc43fb859a063e1b3f936891dafd581892
                    IconTwitters()
                }
                composable(route = Screens.ProfilePage.name) {
                    ProfilePage()
                }
            }
        },
        bottomBar = {
            val backgroundColor: Color = Color(0xFF111111)
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                backgroundColor
            ) {
                val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentDestination: NavDestination? = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem: NavItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = androidx.compose.material3.NavigationBarItemDefaults
                            .colors(
                                selectedIconColor = Color(0xFFF1F1F1),
                                unselectedIconColor =  Color(0xFFF1F1F1),
                                indicatorColor = Color(0xFF111111)
                            ),
//                        modifier = Modifier.background(Color.Transparent),
                        icon = {
                            // Memilih ikon berdasarkan kondisi seleksi
                            val icon =
                                if (currentDestination?.hierarchy?.any { it.route == navItem.route } == true) {
                                    navItem.filledIcon
                                } else {
                                    navItem.icon
                                }
                            // Menampilkan ikon sesuai kondisi
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)

                            )
                        },
                    )
                }
            }
        }
    )
}
