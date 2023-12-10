package com.example.ngebacot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ngebacot.screens.HomePage
import com.example.ngebacot.screens.ProfilePage

@ExperimentalMaterial3Api
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold (
        bottomBar={
            NavigationBar {
                val navBackStackEntry : NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentDestination : NavDestination? = navBackStackEntry?.destination

                listOfNavItems.forEach{navItem : NavItem->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any{it.route == navItem.route}==true,
                        onClick = {
                                  navController.navigate(navItem.route){
                                      popUpTo(navController.graph.findStartDestination().id){
                                          saveState = true
                                      }
                                      launchSingleTop = true
                                      restoreState = true
                                  }
                        },
                        icon = {
                               Icon(
                                   imageVector = navItem.icon,
                                   contentDescription = null
                               )
                        },
                        label = {
                            Text(text = navItem.label)
                        })
                }
            }
        }
    ){
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomePage.name,
            modifier = Modifier
                .padding(paddingValues)
        ){
            composable(route = Screens.HomePage.name){
                HomePage()
            }
            composable(route = Screens.ProfilePage.name){
                ProfilePage()
            }
        }
    }

}