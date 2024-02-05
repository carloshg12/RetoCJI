package com.example.retocji.ui.components.bienvenida

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SolicitarCita(navController: NavController) {

    Text(
        text = "Citas",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold), // Estilo de texto más grande y en negrita
        color = MaterialTheme.colorScheme.primary // Color más vibrante
    )
    Text(
        text = "Solicita una cita para nuestros servicios de asesoría y consultoría.",
        style = MaterialTheme.typography.bodyLarge, // Texto más grande para mejor legibilidad
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 12.dp, bottom = 24.dp) // Aumento del espaciado
    )

    Button(
        onClick = { navController.navigate("Citas") },
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Solicitar una cita",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontSize = 18.sp)
            )
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = "Forward",
                tint = Color.White
            )
        }
    }
}
