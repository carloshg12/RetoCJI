package com.example.retocji.ui.components.topBar

import CitasDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.retocji.ui.viewmodels.UserViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, title: String, userViewModel: UserViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val listaCitas by userViewModel.citasUsuario.observeAsState()
    val cantidadCitas by userViewModel.cantidadCitas.observeAsState()

    userViewModel.citasPorUsuario()
    userViewModel.cantidadCitas()

    LaunchedEffect(key1 = listaCitas?.size) {
        if (showDialog) {
            showDialog = false
            delay(1)
            showDialog = true
        }
    }

    TopAppBar(
        title = { Text(modifier = Modifier.padding(start = 15.dp), text = title) },
        navigationIcon = {
            TopAppBarNavigationIcon(navController, currentDestination)
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(end = 15.dp),
                onClick = { showMenu = !showMenu }) {
                CustomBadgeBox(count = cantidadCitas ?: 0) {
                    Icon(
                        imageVector = Icons.Outlined.AccountBox,
                        contentDescription = "Profile Options",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
            ProfileActionsDropdownMenu(
                showMenu,
                { showMenu = false },
                { showDialog = it },
                navController,
                userViewModel
            )
            if (showDialog) {
                CitasDialog(showDialog, { showDialog = false }, listaCitas, userViewModel)
            }
        }
    )
}