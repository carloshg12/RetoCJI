package com.example.retocji.ui.components.bienvenida

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.retocji.ui.viewmodels.UserViewModel

@Composable
fun Saludo(userViewModel: UserViewModel = hiltViewModel()) {

    val nombreUsuario by userViewModel.userName.observeAsState("Cargando...")

    Column(
        modifier = Modifier.padding(2.dp)
    ) {
        Text(
            text = "Bienvenid@ $nombreUsuario",
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
