package com.example.retocji.ui.screens.logIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.domain.repositories.RegisterUserDTO
import com.example.retocji.ui.viewmodels.logIn.RegistroViewModel

@Composable
fun Registro(navController: NavController, viewModel: RegistroViewModel) {

    // Estados para los campos del formulario
    val username by viewModel.username.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val email by viewModel.email.observeAsState("")
    var buttonClicked by remember { mutableStateOf(false) }


    val registroExitoso by viewModel.registroExitoso.observeAsState()
    val mensajeError by viewModel.mensajeError.observeAsState()


    // Diseño del formulario
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Alineación vertical y horizontal al centro
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = username,
                onValueChange = { viewModel.onUserChanged(it) },
                label = { Text("Nombre de usuario") }
            )
            TextField(
                value = email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )
            TextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    buttonClicked = true
                    val registerUserDTO = RegisterUserDTO(username, email, password)
                    viewModel.registrarUsuario(registerUserDTO)
                    viewModel.login(username, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 38.dp, end = 38.dp)

            ) {
                Text(text = "Registrarse")
            }


            if (registroExitoso == true) {
                LaunchedEffect(Unit) {
                    navController.navigate("GeneralInfo")
                    viewModel.onRegistroChanged(false)
                    buttonClicked = false


                }
                Text(text = "¡Registrado!", color = Color.Green)
            } else if (registroExitoso != null) {

                Text(text = mensajeError!!, color = Color.Red)

            }


        }
    }
}
