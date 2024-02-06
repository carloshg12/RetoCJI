package com.example.retocji.ui.components.gestiones

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocji.ui.viewmodels.GestionesViewModel

@Composable
fun BotonImprimirInformacion(
    citasSeleccionadas: List<Triple<String, String, String>>,
    gestionesViewModel: GestionesViewModel
) {
    val context = LocalContext.current
    Button(
        onClick = { gestionesViewModel.generarPDF(context, citasSeleccionadas) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Imprimir Información",
            fontSize = 16.sp, // Ajusta el tamaño de fuente si es necesario
            fontWeight = FontWeight.Medium
        )
    }
}
