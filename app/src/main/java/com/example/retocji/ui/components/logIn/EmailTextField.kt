package com.example.retocji.ui.components.logIn

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailTextFieldComponent(email: String, onEmailChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChanged,
        label = { Text("Usuario") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
