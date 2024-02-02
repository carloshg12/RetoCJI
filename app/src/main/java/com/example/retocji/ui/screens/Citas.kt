
package com.example.retocji.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun citas() {
    LazyColumn {
        item {
            CitaPersonalizada()
        }
        /*items(getCitasGenericas()) { cita ->
          CitaGenerica(cita)
        }*/
    }
}

@SuppressLint("UnrememberedMutableState")
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
    var gestionDeseada by remember { mutableStateOf("") }
    var asesores = listOf("Marian", "Ionut")
    var expandedAsesores by remember { mutableStateOf(false) }
    var asesorDeseado by remember { mutableStateOf("") }


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

            //si la hora inico es 0 que no se muestre

            if (horaDeseadaInicio != 0 ) {
                //si la hora no esta entre las 8 de la mañana y las 6 de la tarde que no se muestre

                if (horaDeseadaInicio ==8 || horaDeseadaInicio ==9 || horaDeseadaInicio ==10 || horaDeseadaInicio ==11 || horaDeseadaInicio ==13 || horaDeseadaInicio ==14 || horaDeseadaInicio ==15 || horaDeseadaInicio ==16 || horaDeseadaInicio ==17 || horaDeseadaInicio ==18){
                    val formattedTime = String.format("%02d:%02d", horaDeseadaInicio, minutoDeseadoInicio)
                    Text(text = formattedTime, modifier = Modifier.widthIn(min = 100.dp))
                }else{
                    Box(modifier = Modifier.width(100.dp).height(100.dp)){
                        Text(text = "La hora no esta disponible")
                    }
                }
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
            if (horaDeseadaFin != 1){
                val formattedTime = String.format("%02d:%02d", horaDeseadaFin, minutoDeseadoFin)
                Text(text = formattedTime, modifier = Modifier.widthIn(min = 100.dp))
            }
            Spacer(modifier = Modifier.widthIn(min = 115.dp))
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
            selecionAsesor(asesores, mutableStateOf(expandedAsesores), mutableStateOf(asesorDeseado))


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selecionAsesor(
    options: List<String>,
    expanded: MutableState<Boolean>,
    selectedOptionText: MutableState<String>
) {
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText.value,
            onValueChange = {},
            label = { Text("Selecciona un asesor") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText.value = selectionOption
                        expanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
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
        Cita(6, "Sabado", "10:00", "11:00", true, "Ionut", "Divoricio"),
        Cita(7, "Lunes", "8:00", "9:00", true, "Marian", "Gestion de impuestos")
    )
}




