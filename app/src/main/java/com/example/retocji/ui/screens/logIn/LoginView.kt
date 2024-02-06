package com.example.retocji.ui.screens.logIn

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.R
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.logIn.LoginViewModel
import java.time.LocalDateTime

class LoginView {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun login(
        loginViewModel: LoginViewModel,
        citasViewModel: CitasViewModel,
        navController: NavController
    ) {
        val loginResult by loginViewModel.loginResult.observeAsState()
        val loginSuccess by loginViewModel.loginSuccess.observeAsState()
        val email by loginViewModel.email.observeAsState(initial = "")
        val password by loginViewModel.password.observeAsState(initial = "")
        val passwordVisibility by loginViewModel.passwordVisibility.observeAsState(initial = false)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "foto",
                modifier = Modifier.clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(bottom = 30.dp))
            TextField(
                value = email,
                onValueChange = { loginViewModel.onEmailChanged(it) },
                enabled = true,
                placeholder = {
                    Text(
                        text = "Usuario",
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            TextField(
                value = password,
                onValueChange = { loginViewModel.onPasswordChanged(it) },
                enabled = true,
                placeholder = {
                    Text(
                        text = "Contraseña",
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),

                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = loginViewModel.viewIcon()
                    Icon(
                        imageVector = icon,
                        contentDescription = "Visible",
                        tint = Color.White,
                        modifier = Modifier.clickable { loginViewModel.togglePasswordVisibility() }
                    )
                }
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(
                    text = "¿Has olvidado tu contraseña?",
                )
            }


            Button(
                onClick = {
                    loginViewModel.login(email, password) { success ->
                        if (success) {
                            navController.navigate("GeneralInfo")
                            citasViewModel.obtenerGestores()
                        }
                    }


                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Iniciar Sesión")

            }
            Text(text = loginResult.toString())

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .width(135.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "<>",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .width(135.dp)
                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = "¿Aún no te has registrado? Hazlo ya.",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("Registro")

                        }
                        .padding(top = 16.dp)
                )
            }
        }
    }
}