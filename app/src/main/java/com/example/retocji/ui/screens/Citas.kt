
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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.DatePickerState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun citas() {
    var expandedAsesores by remember { mutableStateOf(false) }
    var asesorDeseado by remember { mutableStateOf("") }
    var expandedGestiones by remember { mutableStateOf(false) }
    var gestionDeseada by remember { mutableStateOf("") }
    var expandedHour by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            CitaPersonalizada(
                expandedAsesores = mutableStateOf(expandedAsesores) ,
                asesorDeseado = mutableStateOf(asesorDeseado),
                expandedGestiones = mutableStateOf(expandedGestiones),
                gestionDeseada = mutableStateOf(gestionDeseada),
                expandedHour = mutableStateOf(expandedHour),
                selectedHour = mutableStateOf(selectedHour),
                asesores = listOf("Juan", "Pedro", "Luis"),
                gestiones = listOf("Gestion 1", "Gestion 2", "Gestion 3"),
                showDialog = mutableStateOf(showDialog),
                datePickerState = rememberDatePickerState(),
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaPersonalizada(
    expandedAsesores: MutableState<Boolean>,
    asesorDeseado: MutableState<String>,
    expandedGestiones: MutableState<Boolean>,
    gestionDeseada: MutableState<String>,
    expandedHour: MutableState<Boolean>,
    selectedHour: MutableState<String>,
    asesores: List<String> = listOf(),
    gestiones: List<String> = listOf(),
    showDialog: MutableState<Boolean> ,
    datePickerState: DatePickerState = rememberDatePickerState()
) {
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
            selecionAsesor(
                asesores,
                expandedAsesores,
                asesorDeseado)
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
            selecionGestion(gestiones, expandedGestiones, gestionDeseada, asesorDeseado)
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
                Text(text = SimpleDateFormat("dd/MM/yyyy").format(Date(it)))
            }
            Button(onClick = { if (asesorDeseado.value.isNotEmpty()) showDialog.value = true }) {
                Text("Elegir día")
            }
            if (showDialog.value) {
                DatePickerDialog(
                    onDismissRequest = { showDialog.value = false },
                    confirmButton = {
                        Button(onClick = { showDialog.value = false }) {
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
                            val dayOfWeek = selectedDate.dayOfWeek
                            timestamp > Instant.now().toEpochMilli() && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY
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
            seleccionHoras(expandedHour, selectedHour,asesorDeseado)
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
            Button(onClick = {}) {
                Text("Reservar cita")
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selecionGestion(
    options: List<String>,
    expanded: MutableState<Boolean>,
    selectedOptionText: MutableState<String>,
    asesorDeseado: MutableState<String>
) {
    ExposedDropdownMenuBox(
        expanded = expanded.value && asesorDeseado.value.isNotEmpty(),
        onExpandedChange = { if (asesorDeseado.value.isNotEmpty()) expanded.value = it },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText.value,
            onValueChange = {},
            label = { Text("Selecciona una gestion") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded.value && asesorDeseado.value.isNotEmpty(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun seleccionHoras(
    expanded: MutableState<Boolean>,
    selectedHour: MutableState<String>,
    asesorDeseado: MutableState<String>
) {
    val hoursList = (8..18).map { String.format("%02d:00", it) }

    ExposedDropdownMenuBox(
        expanded = expanded.value && asesorDeseado.value.isNotEmpty(),
        onExpandedChange = { if (asesorDeseado.value.isNotEmpty()) expanded.value = it },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedHour.value,
            onValueChange = {},
            label = { Text("Selecciona una hora") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded.value && asesorDeseado.value.isNotEmpty(),
            onDismissRequest = { expanded.value = false },
        ) {
            hoursList.forEach { hour ->
                DropdownMenuItem(
                    text = { Text(hour) },
                    onClick = {
                        selectedHour.value = hour
                        expanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}