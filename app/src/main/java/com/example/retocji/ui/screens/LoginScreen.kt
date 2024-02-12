package com.example.retocji.ui.screens

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.logIn.LoginViewModel
import android.os.Build
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.components.logIn.LogoComponent
import com.example.retocji.ui.components.logIn.PasswordTextFieldComponent
import com.example.retocji.ui.components.logIn.RegistrationPromptComponent
import com.example.retocji.ui.components.logIn.SignInButtonComponent
import com.example.retocji.ui.components.logIn.UsernameTextFieldComponent
import com.example.retocji.ui.viewmodels.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    citasViewModel: CitasViewModel,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val loginResult by loginViewModel.loginResult.observeAsState()
    val email by loginViewModel.email.observeAsState(initial = "")
    val password by loginViewModel.password.observeAsState(initial = "")
    val passwordVisibility by loginViewModel.passwordVisibility.observeAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LogoComponent()

        Spacer(modifier = Modifier.height(30.dp))

        UsernameTextFieldComponent(username = email, onUsernameChanged = loginViewModel::onEmailChanged)

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextFieldComponent(
            password = password,
            passwordVisibility = passwordVisibility,
            onPasswordChanged = { loginViewModel.onPasswordChanged(it) },
            onTogglePasswordVisibility = { loginViewModel.togglePasswordVisibility() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SignInButtonComponent(onClick = {
            loginViewModel.login(email, password) { success ->
                if (success) {
                    citasViewModel.obtenerGestores()
                    userViewModel.getUserName()
                    userViewModel.citasPorUsuario()
                    navController.navigate("GeneralInfo")
                }
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text("<>", modifier = Modifier.padding(horizontal = 8.dp))
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        RegistrationPromptComponent(onClick = { navController.navigate("Registro") })
    }
}
