package com.example.retocji.ui.screens.logIn

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.ui.components.logIn.PasswordTextFieldComponent
import com.example.retocji.ui.components.logIn.UsernameTextFieldComponent
import com.example.retocji.ui.components.registro.RegisterButtonComponent
import com.example.retocji.ui.components.registro.EmailTextFieldComponent
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.UserNameViewModel
import com.example.retocji.ui.viewmodels.logIn.RegistroViewModel

@Composable
fun Registro(
    navController: NavController,
    viewModel: RegistroViewModel,
    userNameViewModel: UserNameViewModel,
    citasViewModel: CitasViewModel
) {
    val username by viewModel.username.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val email by viewModel.email.observeAsState("")
    var passwordVisibility by remember { mutableStateOf(false) }
    val registroExitoso by viewModel.registroExitoso.observeAsState()
    val mensajeError by viewModel.mensajeError.observeAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            UsernameTextFieldComponent(username = username, onUsernameChanged = viewModel::onUserChanged)
            EmailTextFieldComponent(email = email, onEmailChanged = viewModel::onEmailChanged)
            PasswordTextFieldComponent(password = password, passwordVisibility = passwordVisibility, onPasswordChanged = viewModel::onPasswordChanged, onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility })
            RegisterButtonComponent(viewModel = viewModel, username = username, email = email, password = password)

            when {
                registroExitoso == true -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("GeneralInfo")
                        viewModel.onRegistroChanged(false)
                        userNameViewModel.setUserName(username)
                        viewModel.login(username, password)
                        citasViewModel.obtenerGestores()
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
