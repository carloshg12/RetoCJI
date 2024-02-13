package com.example.retocji.ui.components.citas

import SeleccionHoras
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
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

        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text("Advertencia", style = MaterialTheme.typography.headlineSmall) },
                text = {
                    Text(
                        "Por favor, selecciona un asesor primero.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    Button(onClick = { showAlert = false }, modifier = Modifier.fillMaxWidth()) {
                        Text("OK", style = MaterialTheme.typography.labelLarge)
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                textContentColor = MaterialTheme.colorScheme.onBackground,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Asesor", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            SelecionAsesor(
                options = asesores,
                onAsesorSelected = citasViewModel::setAsesorDeseado
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Gestion", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            SelecionGestion(
                gestiones,
                onGestionSelected = { gestion ->
                    //gestionDeseada.value = gestion
                    citasViewModel.setGestionDeseada(gestion)
                }, asesorDeseado
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Día", modifier = Modifier.widthIn(min = 50.dp))
            diaDeseado?.let {
                val dateSelected = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                val fecha = SimpleDateFormat("yyyy-MM-dd").format(Date(dateSelected))
                citasViewModel.setSelectedDate(fecha)
                Text(text = dateSelected)
            }
            Button(onClick = { if (asesorDeseado.isNotEmpty()) showDialog.value = true }) {
                Text("Elegir día")
            }
            if (showDialog.value) {
                DatePickerDialog(
                    onDismissRequest = { showDialog.value = false },
                    confirmButton = {
                        Button(onClick = {
                            citasViewModel.setFechaSeleccionada(
                                Instant.ofEpochMilli(
                                    datePickerState.selectedDateMillis!!
                                ).atZone(ZoneId.systemDefault()).toLocalDate()
                            )
                            showDialog.value = false
                        }) {
                            Text("Confirmar")
                        }
                    }, dismissButton = {
                        OutlinedButton(onClick = { showDialog.value = false }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        dateValidator = { timestamp ->
                            val selectedDate =
                                Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            val today = LocalDate.now(ZoneId.systemDefault())
                            val dayOfWeek = selectedDate.dayOfWeek

                            (selectedDate.isEqual(today) || selectedDate.isAfter(today)) && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY
                        },
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hora", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            SeleccionHoras(
                selectedHour = selectedHour,
                onHourSelected = { hour ->
                    selectedHour.value = hour
                },
                asesorDeseado = asesorDeseado,
                horas,
                citasViewModel
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (asesorDeseado.isEmpty() || selectedHour.value.isEmpty() || selectedDateTime.isEmpty()) {
                        validateFields = "false"
                    } else {
                        citasViewModel.crearCita(asesorDeseado, selectedDateTime, selectedHour.value)
                        validateFields = "true"
                    }



                }) {
                Text("Reservar cita")
            }

        }

        LaunchedEffect(responseMessage) {
            if (responseMessage == "Cita creada exitosamente") {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                format.timeZone = TimeZone.getDefault()

                val dateTime = format.parse("$fechaSeleccionada ${selectedHour.value}") ?: return@LaunchedEffect

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