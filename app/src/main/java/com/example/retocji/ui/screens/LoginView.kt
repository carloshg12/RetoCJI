package com.example.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.retocji.R
import com.example.retocji.domain.repositories.Citas
import com.example.retocji.domain.repositories.CitasDTO
import com.example.retocji.domain.repositories.UsersDTO
import java.time.LocalDateTime

class LoginView {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun login(loginViewModel: LoginViewModel) {
        val loginResult by loginViewModel.loginResult.observeAsState()
        val email by loginViewModel.email.observeAsState(initial = "")
        val password by loginViewModel.password.observeAsState(initial = "")
        val passwordVisibility by loginViewModel.passwordVisibility.observeAsState(initial = false)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "foto",
                modifier = Modifier.clip(CircleShape)
            )
            TextField(
                value = email,
                onValueChange = { loginViewModel.onEmailChanged(it) },
                enabled = true,
                placeholder = {
                    Text(
                        text = "Email",
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
                        text = "Password",
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
            val cita = CitasDTO(

                horaInicio = LocalDateTime.now().toString(), // La hora de inicio actual
                horaFin = LocalDateTime.now().plusHours(1).toString(), // La hora de fin, una hora después
                usuario = UsersDTO(
                    id = 1,
                    name = "Jose",
                    email = "aluebr5363@ieselcaminas.org"
                ), // Un usuario de ejemplo
                gestor = UsersDTO(
                    id = 1,
                    name = "Jose",
                    email = "aluebr5363@ieselcaminas.org"
                ) // Un gestor de ejemplo
            )

            Button(
                onClick = {
                    loginViewModel.login(email, password)
                    loginViewModel.crearCita(
                        cita
                    )
                    loginViewModel.userProfile()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login In")

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
                        .width(165.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "OR",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .width(165.dp)
                )
            }
        }
    }
}