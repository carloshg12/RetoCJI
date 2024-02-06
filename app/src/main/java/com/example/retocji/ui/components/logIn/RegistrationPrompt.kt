package com.example.retocji.ui.components.logIn

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegistrationPromptComponent(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("¿Aún no te has registrado? Hazlo ya.", color = MaterialTheme.colorScheme.primary)
    }
}
