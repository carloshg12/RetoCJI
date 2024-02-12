package com.example.retocji.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.retocji.ui.viewmodels.UserNameViewModel
import androidx.compose.ui.unit.dp
import com.google.firebase.database.collection.LLRBNode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, title: String,userNameViewModel: UserNameViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val listaCitas by userNameViewModel.citasUsuario.observeAsState()
    val cantidadCitas by userNameViewModel.cantidadCitas.observeAsState()
    userNameViewModel.citasPorUsuario()
    userNameViewModel.cantidadCitas()

    TopAppBar(
        modifier = Modifier.padding(start=10.dp),
        title = { Text(text = title) },
        navigationIcon = if (currentDestination?.route != "GeneralInfo") {
            {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else {
            {}
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu },
                modifier = Modifier.padding(end=10.dp)) {
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

                ){
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
                    verticalAlignment = Alignment.CenterVertically
                    ,modifier = Modifier.clickable {
                        navController.navigate("LogIn")
                        userNameViewModel.exit()
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
                        title = { Text("Información de Citas") },
                        text = {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                val ahora = LocalDateTime.now()

                                listaCitas
                                    ?.filter { cita -> LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME).isAfter(ahora) }
                                    ?.sortedBy { cita -> LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }
                                    ?.forEach { cita ->
                                        val fechaHoraObj = LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                        val fechaFormateada = fechaHoraObj.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                        val horaFormateada = fechaHoraObj.format(DateTimeFormatter.ofPattern("HH:mm"))
                                        Text(buildAnnotatedString {
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Día: ")
                                            }
                                            append("$fechaFormateada\n")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Hora: ")
                                            }
                                            append("$horaFormateada\n")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Tipo: ")
                                            }
                                            append("${cita.tipoCita.nombre}\n")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Gestor: ")
                                            }
                                            append("${cita.gestor.name}\n")
                                        })
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }

                            }
                        },
                        confirmButton = {
                            Button(onClick = { showDialog = false }) {
                                Text("Cerrar")
                            }
                        }
                    )
                }

            }
        }
    )
}
