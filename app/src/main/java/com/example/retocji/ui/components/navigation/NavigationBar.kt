package com.example.retocji.ui.components.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun NavigationBar(navController: NavController, items: List<Navigation_Data>) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    BottomNavigation(backgroundColor = Color.LightGray) {
        items.forEach { item ->
            BottomNavigationItem(
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == item.route,
                icon = {
                    androidx.compose.material.Icon(
                        imageVector = item.icon,
                        contentDescription = item.text
                    )
                },
                label = { androidx.compose.material.Text(text = item.text) },
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}