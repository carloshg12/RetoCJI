package com.example.retocji.ui.components.informacion

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun Encabezado(texto: String) {
    Text(
        text = texto,
        fontSize = 28.sp,
        style = MaterialTheme.typography.bodyMedium
    )
}
