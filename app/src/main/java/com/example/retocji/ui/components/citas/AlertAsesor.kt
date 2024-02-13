package com.example.retocji.ui.components.citas

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlertAsesor(showAlert: Boolean) {
    var showAlert1 = showAlert
    if (showAlert1) {
        AlertDialog(
            onDismissRequest = { showAlert1 = false },
            title = { Text("Advertencia", style = MaterialTheme.typography.headlineSmall) },
            text = {
                Text(
                    "Por favor, selecciona un asesor primero.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(onClick = { showAlert1 = false }, modifier = Modifier.fillMaxWidth()) {
                    Text("OK", style = MaterialTheme.typography.labelLarge)
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            textContentColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}