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
import seleccionHoras
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.util.Date

@SuppressLint("UnrememberedMutableState")
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CitaPersonalizada(
    expandedAsesores: MutableState<Boolean>,
    asesorDeseado: String,
    expandedGestiones: MutableState<Boolean>,
    gestionDeseada: MutableState<String>,
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
                selecionGestion(gestiones,
                    onGestionSelected = {gestion ->
                    gestionDeseada.value = gestion
                } ,  asesorDeseado)
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
                Button(onClick = { if (asesorDeseado.isNotEmpty()) showDialog.value = true }) {
                    Text("Elegir día")
                }
                if (showDialog.value) {
                    DatePickerDialog(
                        onDismissRequest = { showDialog.value = false },
                        confirmButton = {
                            Button(onClick = {
                                citasViewModel.setFechaSeleccionada(Instant.ofEpochMilli(
                                    datePickerState.selectedDateMillis!!
                                ).atZone(ZoneId.systemDefault()).toLocalDate())
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
                                val dayOfWeek = selectedDate.dayOfWeek
                                timestamp > Instant.now()
                                    .toEpochMilli() && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY
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
                seleccionHoras(
                    //expanded = expandedHour,
                    selectedHour = selectedHour,
                    onHourSelected = { hour ->
                        selectedHour.value = hour
                    },
                    asesorDeseado = asesorDeseado,
                    //citas = citas,
                    //diaDeseado = diaDeseado,
                    horas
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
                Button(onClick = {}) {
                    Text("Reservar cita")
                }
            }
        }
    }