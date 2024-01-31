
package com.example.retocji.ui.screens

import android.app.TimePickerDialog
import android.os.Build
import android.text.Layout
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.retocji.domain.models.Cita
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun citas() {
    LazyColumn {
        item {
            CitaPersonalizada()
        }
        items(getCitasGenericas()) { cita ->
          CitaGenerica(cita)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaPersonalizada() {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var showDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var diaDeseado = datePickerState.selectedDateMillis
    var horaDeseadaInicio = timePickerState.hour
    var minutoDeseadoInicio = timePickerState.minute
    var horaDeseadaFin = timePickerState.hour + 1
    var minutoDeseadoFin = timePickerState.minute
    var asesorDeseado by remember { mutableStateOf("") }
    var gestionDeseada by remember { mutableStateOf("") }
    var aseores = listOf("Marian", "Ionut")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Día", modifier = Modifier.widthIn(min = 50.dp))
            diaDeseado?.let {
                Text(text = SimpleDateFormat("dd/MM/yyyy").format(Date(it)))
            }
            Button(onClick = { showDialog = true }) {
                Text("Elegir día")
            }
            if (showDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Confirmar")
                        }
                    }, dismissButton = {
                        OutlinedButton(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        dateValidator = { timestamp -> timestamp > Instant.now().toEpochMilli() },
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
            Text(text = "Hora Inicio", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            horaDeseadaInicio?.let {
                Text(text = "$horaDeseadaInicio:$minutoDeseadoInicio", modifier = Modifier.widthIn(min = 100.dp))
            }
            Button(onClick = { showTimePicker = true }) {
                Text("Elegir hora")
            }
            if (showTimePicker) {
                TimePickerDialog(
                    onCancel = { showTimePicker = false },
                    onConfirm = {
                        showTimePicker = false
                    },
                    content = {
                        TimePicker(
                            state = timePickerState,
                        )
                    }
                )
            }
        }

        // Sección para poner hora fin
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hora fin", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$horaDeseadaFin:$minutoDeseadoFin", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.widthIn(min = 115.dp)) // Espacio en blanco a la derecha
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
            TextField(
                value = gestionDeseada,
                onValueChange = { gestionDeseada = it },
                label = { Text("Especifica que servicio desdeas") }
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
            TextField(
                value = asesorDeseado,
                onValueChange = { asesorDeseado = it },
                label = { Text("Seleciona un asesor") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {}) {
                Text("Reservar cita")
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}


@Composable
fun CitaGenerica(cita: Cita) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dia: ${cita.dia}")
        Text(text = "Hora: ${cita.inicio} - ${cita.fin}")
        Text(text = "Asesor: ${cita.asesor}")
        Text(text = "Gestion: ${cita.gestion}")
        Button(onClick = {}) {
            Text("Reservar cita")
        }
    }
}

fun getCitasGenericas(): List<Cita> {
    return listOf(
        Cita(1, "Lunes", "8:00", "9:00", true, "Marian", "Gestion de impuestos"),
        Cita(2, "Martes", "16:00", "17:00", true, "Ionut", "Divoricio"),
        Cita(3, "Miercoles", "10:00", "11:00", true, "Marian", "Renta"),
        Cita(4, "Jueves", "8:00", "9:00", true, "Ionut", "Renta"),
        Cita(5, "Viernes", "16:00", "17:00", true, "Marian", "Renta"),
        Cita(6, "Domingo", "10:00", "11:00", true, "Ionut", "Divoricio"),
        Cita(7, "Lunes", "8:00", "9:00", true, "Marian", "Gestion de impuestos")
    )
}


