package com.example.retocji.ui.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.retocji.ui.components.topBar.CustomBadgeBox
import com.example.retocji.ui.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, title: String, userViewModel: UserViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val listaCitas by userViewModel.citasUsuario.observeAsState()
    val cantidadCitas by userViewModel.cantidadCitas.observeAsState()

    userViewModel.citasPorUsuario()
    userViewModel.cantidadCitas()
    LaunchedEffect(key1 = listaCitas?.size) {
        if (showDialog) {
            showDialog = false
            delay(1)
            showDialog = true
        }
    }

    TopAppBar(

        title = { Text(modifier = Modifier.padding(start = 15.dp),text = title) },
        navigationIcon = if (currentDestination?.route != "GeneralInfo") {
            {
                IconButton(modifier = Modifier.padding(5.dp), onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            IconButton(modifier = Modifier.padding(end = 15.dp),onClick = { showMenu = !showMenu }) {
                CustomBadgeBox(count = cantidadCitas ?: 0) {
                    Icon(
                        imageVector = Icons.Outlined.AccountBox,
                        contentDescription = "Profile Options",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { showDialog = true }

                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("   Citas    ")
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Citas"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                        navController.navigate("LogIn")
                        userViewModel.exit()
                    }
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Text("   Salir     ")
                    Icon(
                        imageVector = Icons.Outlined.ExitToApp,
                        contentDescription = "Salir"
                    )
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Información de Citas",
                                    Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { showDialog = false },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(Modifier.background(color = MaterialTheme.colorScheme.primary)){
                                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MaterialTheme.colorScheme.surface)
                                    }
                                }
                            }
                        },
                        text = {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                val ahora = LocalDateTime.now()

                                listaCitas
                                    ?.filter { cita ->
                                        LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME).isAfter(ahora)
                                    }
                                    ?.sortedBy { cita ->
                                        LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                    }
                                    ?.forEach { cita ->
                                        val fechaHoraObj = LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                        val fechaFormateada = fechaHoraObj.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                        val horaFormateada = fechaHoraObj.format(DateTimeFormatter.ofPattern("HH:mm"))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Día: ") }
                                                append("$fechaFormateada\n")
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Hora: ") }
                                                append("$horaFormateada\n")
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Tipo: ") }
                                                append("${cita.tipoCita.nombre}\n")
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Gestor: ") }
                                                append("${cita.gestor.name}\n")
                                            }, modifier = Modifier.weight(1f))

                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Borrar cita",
                                                tint = Color.Red,
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable(onClick = {})
                                                    .pointerInput(Unit) {
                                                        detectTapGestures(
                                                            onLongPress = {
                                                                Log.e(
                                                                    "FechaFormateada",
                                                                    cita.horaInicio
                                                                )
                                                                userViewModel.borrarCitaPorUsuario(
                                                                    cita.horaInicio
                                                                )
                                                            }
                                                        )
                                                    }
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                            }
                        },
                        confirmButton = {}
                    )
                }

            }
        }
    )
}