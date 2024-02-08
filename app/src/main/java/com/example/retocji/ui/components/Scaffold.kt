package com.example.retocji.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.retocji.ui.components.navigation.NavigationBar
import com.example.retocji.ui.components.navigation.getInfoAbout
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.UserNameViewModel

@Composable
fun Scaffold(
    navController: NavController,
    userNameViewModel: UserNameViewModel,
    citasViewModel: CitasViewModel,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController, "I&M Asesores",userNameViewModel)
        },
        bottomBar = {
            NavigationBar(navController, getInfoAbout(),citasViewModel)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}