package com.example.retocji.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun NavigationBar(
    dataaboutList: List<Navigation_Data>,
    navController: NavController,
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = dataaboutList.map { it.text }

    androidx.compose.material3.NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        dataaboutList[index].icon,
                        contentDescription = item
                    )
                },
                label = { Text(text = item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(dataaboutList[index].route)
                }
            )
        }

    }
}