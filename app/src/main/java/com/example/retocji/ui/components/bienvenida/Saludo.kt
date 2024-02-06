package com.example.retocji.ui.components.bienvenida

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocji.ui.viewmodels.UserNameViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Saludo(userNameViewModel: UserNameViewModel = hiltViewModel()) {

    val nombreUsuario by userNameViewModel.userName.observeAsState("Cargando...")

    Column(
        modifier = Modifier.padding(2.dp)
    ) {
        Text(
            text = "Bienvenid@, $nombreUsuario",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                letterSpacing = 0.15.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
