package com.example.retocji.ui.screens.logIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.ui.components.logIn.PasswordTextFieldComponent
import com.example.retocji.ui.components.logIn.UsernameTextFieldComponent
import com.example.retocji.ui.components.registro.BackToLoginButtonComponent
import com.example.retocji.ui.components.registro.EmailTextFieldComponent
import com.example.retocji.ui.components.registro.RegisterButtonComponent
import com.example.retocji.ui.viewmodels.RegistroViewModel
import com.example.retocji.ui.viewmodels.UserViewModel

@Composable
fun Registro(
    navController: NavController,
    viewModel: RegistroViewModel,
    userViewModel: UserViewModel,

    ) {
    val username by viewModel.username.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val email by viewModel.email.observeAsState("")
    var passwordVisibility by remember { mutableStateOf(false) }
    val registroExitoso by viewModel.registroExitoso.observeAsState()
    val mensajeError by viewModel.mensajeError.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            UsernameTextFieldComponent(
                username = username,
                onUsernameChanged = viewModel::onUserChanged
            )
            EmailTextFieldComponent(email = email, onEmailChanged = viewModel::onEmailChanged)
            PasswordTextFieldComponent(
                password = password,
                passwordVisibility = passwordVisibility,
                onPasswordChanged = viewModel::onPasswordChanged,
                onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility })


            Row(verticalAlignment = Alignment.CenterVertically) {
                BackToLoginButtonComponent(navController)
                Spacer(modifier = Modifier.width(10.dp))
                RegisterButtonComponent(
                    viewModel = viewModel,
                    username = username,
                    email = email,
                    password = password
                )
            }

            when {
                registroExitoso == true -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("GeneralInfo")
                        viewModel.onRegistroChanged(false)
                        userViewModel.setUserName(username)
                        viewModel.login(username, password,email)
                    }
                    Text(text = "¡Registrado con éxito!", color = Color.Green)
                }

                mensajeError != null -> {
                    Text(text = mensajeError!!, color = Color.Red)
                }
            }
        }
    }
}


