package com.example.retocji.ui.components.registro

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.domain.models.logIn.RegisterUserDTO
import com.example.retocji.ui.viewmodels.RegistroViewModel

@Composable
fun RegisterButtonComponent(
    viewModel: RegistroViewModel,
    username: String,
    email: String,
    password: String
) {
    Button(
        onClick = {
            viewModel.registrarUsuario(RegisterUserDTO(username, email, password))
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Registrarse")
    }
}
