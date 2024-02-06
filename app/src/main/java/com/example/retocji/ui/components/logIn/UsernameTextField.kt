package com.example.retocji.ui.components.logIn

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UsernameTextFieldComponent(username: String, onUsernameChanged: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChanged,
        label = { Text("Nombre de usuario") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
