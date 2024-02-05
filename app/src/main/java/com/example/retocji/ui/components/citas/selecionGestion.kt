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