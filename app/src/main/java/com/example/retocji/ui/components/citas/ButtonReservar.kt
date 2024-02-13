package com.example.retocji.ui.components.citas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.viewmodels.CitasViewModel

@SuppressLint("NewApi")
@Composable
fun ButtonReservar(
    asesorDeseado: String,
    selectedHour: MutableState<String>,
    selectedDateTime: String,
    validateFields: String,
    citasViewModel: CitasViewModel
): String {
    var validateFields1 = validateFields
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
                    validateFields1 = "false"
                } else {
                    citasViewModel.crearCita(
                        asesorDeseado,
                        selectedDateTime,
                        selectedHour.value
                    )
                    validateFields1 = "true"
                }


            }) {
            Text("Reservar cita")
        }

    }
    return validateFields1
}