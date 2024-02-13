package com.example.retocji.ui.components.citas

import SeleccionHoras
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retocji.ui.components.GoogleCalendar.agreagarCitaCalendario
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaPersonalizada(
    asesorDeseado: String,
    selectedHour: MutableState<String>,
    asesores: List<String> = listOf(),
    gestiones: List<String> = listOf(),
    showDialog: MutableState<Boolean>,
    datePickerState: DatePickerState = rememberDatePickerState(),
    citasViewModel: CitasViewModel,
    horas: List<String>?,
    userViewModel: UserViewModel
) {
    val selectedDateTime by citasViewModel.selectedDate.collectAsState()
    val fechaSeleccionada by citasViewModel.fechaSeleccionada.collectAsState()

    var validateFields by remember { mutableStateOf("") }
    val responseMessage by citasViewModel.responseMessage.collectAsState()
    var showMaxCitasDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var diaDeseado by remember { mutableStateOf(datePickerState.selectedDateMillis) }
        LaunchedEffect(datePickerState.selectedDateMillis) {
            diaDeseado = datePickerState.selectedDateMillis
        }
        var showAlert by remember { mutableStateOf(false) }

        AlertAsesor(showAlert)

        SelecionAsesor(
            options = asesores,
            onAsesorSelected = citasViewModel::setAsesorDeseado
        )

        SelecionGestion(
            gestiones,
            onGestionSelected = { gestion ->
                citasViewModel.setGestionDeseada(gestion)
            }, asesorDeseado
        )

        SeleccionFecha(diaDeseado, citasViewModel, asesorDeseado, showDialog, datePickerState)

        SeleccionHoras(
            selectedHour = selectedHour,
            onHourSelected = { hour ->
                selectedHour.value = hour
            },
            asesorDeseado = asesorDeseado,
            horas,
            citasViewModel
        )

        validateFields = ButtonReservar(
            asesorDeseado,
            selectedHour,
            selectedDateTime,
            validateFields,
            citasViewModel
        )

        LaunchedEffect(responseMessage) {
            if (responseMessage == "Cita creada exitosamente") {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                format.timeZone = TimeZone.getDefault()

                val dateTime = format.parse("$fechaSeleccionada ${selectedHour.value}")
                    ?: return@LaunchedEffect

                val beginTime = Calendar.getInstance().apply {
                    timeInMillis = dateTime.time
                    Log.e("BeginTimeInMillis", timeInMillis.toString())
                }

                val endTime = Calendar.getInstance().apply {
                    timeInMillis = dateTime.time + TimeUnit.HOURS.toMillis(1)
                }

                userViewModel.cantidadCitas()
                userViewModel.citasPorUsuario()
                agreagarCitaCalendario(
                    "Cita $asesorDeseado",
                    "Oficina I&M Asesores",
                    "Cita I&M Asesores",
                    beginTime.timeInMillis,
                    endTime.timeInMillis,
                    context
                )
            }
        }

        if (validateFields == "false") {
            Text(
                text = "Hay campos vacíos",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        } else if (!responseMessage.isNullOrBlank()) {
            if (responseMessage != "Cita creada exitosamente") {
                AlertDialog(
                    onDismissRequest = { showMaxCitasDialog = false },
                    title = {
                        Text(
                            "Error al solicitar cita",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 22.sp
                            )
                        )
                    },
                    text = {
                        Text(
                            responseMessage ?: "ERROR",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            )
                        )
                    },
                    confirmButton = {}

                )

            }
            citasViewModel.actualizarHorasDisponibles()
            LaunchedEffect(responseMessage) {
                delay(3500)
                citasViewModel.clearResponseMessage()
            }
        }

    }
}