package com.example.retocji.ui.components.citas

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.ui.viewmodels.CitasViewModel
import SeleccionHoras
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.retocji.ui.components.GoogleCalendar.agreagarCitaCalendario
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
    expandedAsesores: MutableState<Boolean>,
    asesorDeseado: String,
    expandedGestiones: MutableState<Boolean>,
    gestionDeseada: String,
    expandedHour: MutableState<Boolean>,
    selectedHour: MutableState<String>,
    asesores: List<String> = listOf(),
    gestiones: List<String> = listOf(),
    showDialog: MutableState<Boolean>,
    datePickerState: DatePickerState = rememberDatePickerState(),
    citas: List<CitasDTO>,
    citasViewModel: CitasViewModel,
    horas: List<String>?
) {
    val selectedDate by citasViewModel.selectedDate.collectAsState()
    var validateFields by remember { mutableStateOf("") }
    val responseMessage by citasViewModel.responseMessage.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var diaDeseado by remember { mutableStateOf(datePickerState.selectedDateMillis) }
        LaunchedEffect(datePickerState.selectedDateMillis) {
            diaDeseado = datePickerState.selectedDateMillis
        }
        var showAlert by remember { mutableStateOf(true) }


        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text("Advertencia") },
                text = { Text("Por favor, selecciona un asesor primero.") },
                confirmButton = {
                    Button(onClick = { showAlert = false }) {
                        Text("OK")
                    }
                }
            )
        }

        //selecion asesor
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


        //selecion gestor
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Gestion", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            SelecionGestion(gestiones,
                onGestionSelected = { gestion ->
                    //gestionDeseada.value = gestion
                    citasViewModel.setGestionDeseada(gestion)
                }, asesorDeseado)
        }


        //selecion dia
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
                            val selectedDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
                            val today = LocalDate.now(ZoneId.systemDefault())
                            val dayOfWeek = selectedDate.dayOfWeek

                            // Permite seleccionar el día actual y los días futuros, excluyendo sábados y domingos.
                            (selectedDate.isEqual(today) || selectedDate.isAfter(today)) && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY
                        },
                    )
                }
            }
        }

        // Sección para poner hora inicio
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hora Inicio", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            SeleccionHoras(
                //expanded = expandedHour,
                selectedHour = selectedHour,
                onHourSelected = { hour ->
                    selectedHour.value = hour
                },
                asesorDeseado = asesorDeseado,
                //citas = citas,
                //diaDeseado = diaDeseado,
                horas,
                citasViewModel
            )
        }

        // Sección para poner hora fin
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hora Fin", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))

            if (selectedHour.value.isNotEmpty()) {
                val horaFin = selectedHour.value.split(":")[0].toInt() + 1
                Text(text = String.format("%02d:00", horaFin))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            Button(onClick = {
                if (asesorDeseado.isEmpty() || selectedHour.value.isEmpty() || selectedDate.isEmpty()) {
                    validateFields = "false"
                } else {
                    citasViewModel.crearCita(asesorDeseado, selectedDate, selectedHour.value)
                    validateFields = "true"
                }

                // Asumiendo que `selectedDate` está en formato "yyyy-MM-dd" y `selectedHour.value` en "HH:mm"
                //val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val format = SimpleDateFormat("yyyy-dd-MM HH:mm", Locale.getDefault())

                format.timeZone = TimeZone.getDefault() // Asegúrate de que la zona horaria sea la correcta
                val dateTime = format.parse("$selectedDate ${selectedHour.value}") ?: return@Button

                val beginTime = Calendar.getInstance().apply {
                    timeInMillis = dateTime.time
                }

                val endTime = Calendar.getInstance().apply {
                    timeInMillis = dateTime.time + TimeUnit.HOURS.toMillis(1)
                }

                agreagarCitaCalendario(
                    "Cita $asesorDeseado",
                    "Oficina I&M Asesores",
                    "Cita I&M Asesores",
                    beginTime.timeInMillis,
                    endTime.timeInMillis,
                    context
                )

            }) {
                Text("Reservar cita")
            }

        }

        if(validateFields == "false") {
            Text(text = "Hay campos vacíos",color=Color.Red)

            LaunchedEffect(validateFields) {
                delay(5000)
                validateFields = ""
            }
        } else if (!responseMessage.isNullOrBlank()) {

            Text(text = responseMessage!!,
                color = if (responseMessage.equals("Cita creada exitosamente"))
                    Color.Green else Color.Red )
            citasViewModel.actualizarHorasDisponibles()
            LaunchedEffect(responseMessage) {
                delay(5000)
                citasViewModel.clearResponseMessage()
            }
        }


    }
}