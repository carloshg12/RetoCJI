package com.example.retocji.ui.components.citas

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

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