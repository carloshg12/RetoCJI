package com.example.retocji.ui.components.logIn

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun PasswordTextFieldComponent(password: String, passwordVisibility: Boolean, onPasswordChanged: (String) -> Unit, onTogglePasswordVisibility: () -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChanged,
        label = { Text("Contraseña") },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = onTogglePasswordVisibility) {
                Icon(imageVector = image, contentDescription = "Toggle password visibility")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
