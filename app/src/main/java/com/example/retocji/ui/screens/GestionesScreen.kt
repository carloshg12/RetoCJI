package com.example.retocji.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.components.gestiones.BotonImprimirInformacion
import com.example.retocji.ui.components.gestiones.CarritoCitas
import com.example.retocji.ui.components.gestiones.ListaCitas
import com.example.retocji.ui.viewmodels.GestionesViewModel

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
