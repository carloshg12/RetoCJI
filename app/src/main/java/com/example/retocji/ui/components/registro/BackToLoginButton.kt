package com.example.retocji.ui.components.registro

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BackToLoginButtonComponent(navController: NavController) {
    Button(
        onClick = { navController.navigate("LogIn") },
        colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.surface
        ),

        ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Ir atrás"
        )
    }
}