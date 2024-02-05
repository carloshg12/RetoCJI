package com.example.retocji.ui.components.gestiones

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CarritoCitas(citasSeleccionadas: List<Triple<String, String, String>>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Citas Seleccionadas:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (citasSeleccionadas.isNotEmpty()) {
            citasSeleccionadas.forEach { cita ->
                Text(
                    "- ${cita.first}: ${cita.third}",
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                )
            }
        } else {
            Text("No hay citas seleccionadas.", fontSize = 16.sp)
        }
    }
}
