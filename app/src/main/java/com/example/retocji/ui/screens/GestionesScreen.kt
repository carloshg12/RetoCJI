package com.example.retocji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.components.gestiones.BotonImprimirInformacion
import com.example.retocji.ui.components.gestiones.CarritoCitas
import com.example.retocji.ui.components.gestiones.ListaCitas
import com.example.retocji.ui.viewmodels.GestionesViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun GestionesScreen(gestionesViewModel: GestionesViewModel) {
    Scaffold { innerPadding ->
        val citasSeleccionadas = remember { mutableStateListOf<Triple<String, String, String>>() }
        Column(Modifier.padding(innerPadding)) {
            ListaCitas(Modifier.weight(1f), citasSeleccionadas, gestionesViewModel)
            Divider(color = Color.LightGray, thickness = 1.dp)
            CarritoCitas(citasSeleccionadas)
            if (citasSeleccionadas.isNotEmpty()) {
                BotonImprimirInformacion(citasSeleccionadas, gestionesViewModel)
            }
        }
    }
}
