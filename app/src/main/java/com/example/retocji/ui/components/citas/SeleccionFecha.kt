package com.example.retocji.ui.components.citas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.viewmodels.CitasViewModel
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@SuppressLint("NewApi")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SeleccionFecha(
    diaDeseado: Long?,
    citasViewModel: CitasViewModel,
    asesorDeseado: String,
    showDialog: MutableState<Boolean>,
    datePickerState: DatePickerState
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
}