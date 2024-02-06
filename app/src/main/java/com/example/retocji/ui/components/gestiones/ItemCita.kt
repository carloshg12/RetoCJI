package com.example.retocji.ui.components.gestiones

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemCita(
    cita: Triple<String, String, String>,
    citasSeleccionadas: MutableList<Triple<String, String, String>>
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .clickable { expanded = !expanded }
            .padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = cita.first,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = cita.second,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = cita.third,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007F0E)
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        if (cita !in citasSeleccionadas) citasSeleccionadas.add(cita)
                        else citasSeleccionadas.remove(cita)
                    }) {
                        Text(
                            if (cita !in citasSeleccionadas) "Añadir" else "Eliminar",
                            fontSize = 16.sp, // Ajusta el tamaño de fuente si es necesario
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = if (cita !in citasSeleccionadas) Icons.Filled.Add else Icons.Filled.Remove,
                            contentDescription = if (cita !in citasSeleccionadas) "Añadir" else "Eliminar"
                        )
                    }
                }
            }
        }
    }
}
