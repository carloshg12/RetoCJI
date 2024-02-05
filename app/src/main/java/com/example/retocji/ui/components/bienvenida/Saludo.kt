package com.example.retocji.ui.components.bienvenida

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.retocji.ui.viewmodels.CitasViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun Saludo(citasViewModel: CitasViewModel) {
    Column(
        modifier = Modifier.padding(2.dp)
    ) {
        Text(
            text = "Bienvenid@, ${citasViewModel.obtenerTokenDesdeSharedPreferences()}",
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