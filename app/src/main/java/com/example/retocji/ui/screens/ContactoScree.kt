package com.example.retocji.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocji.ui.viewmodels.ContactoViewModel
import com.example.retocji.ui.viewmodels.UserViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaContacto(contactoViewModel: ContactoViewModel, userViewModel: UserViewModel) {
    var asunto by remember { mutableStateOf(TextFieldValue("")) }
    var mensaje by remember { mutableStateOf(TextFieldValue("")) }
    var showConfirmation by remember { mutableStateOf(false) }

    userViewModel.getUserEmail()
    val userEmail by userViewModel.userEmail.observeAsState()
    val userName by userViewModel.userName.observeAsState()

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formulario de Contacto",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp),
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = asunto,
                onValueChange = { asunto = it },
                label = { Text("Asunto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = { Text("Mensaje") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                singleLine = false,
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    contactoViewModel.sendEmail(userName?:"",userEmail.toString(),asunto.text,mensaje.text)
                    showConfirmation = true
                          },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Enviar")
            }
            if (showConfirmation) {
                Text(
                    "Mensaje enviado con éxito",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 18.sp
                )
            }
            LaunchedEffect(showConfirmation) {
                if (showConfirmation) {
                    delay(3000)
                    showConfirmation = false
                    asunto = TextFieldValue("")
                    mensaje = TextFieldValue("")
                }
            }
        }
    }
}