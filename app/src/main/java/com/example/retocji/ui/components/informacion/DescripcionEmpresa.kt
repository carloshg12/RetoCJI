package com.example.retocji.ui.components.informacion

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun DescripcionEmpresa(descripcion: String) {
    Text(
        text = descripcion,
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth()
    )
}
