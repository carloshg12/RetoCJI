package com.example.retocji.ui.components.gestiones

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.retocji.ui.viewmodels.GestionesViewModel

@Composable
fun ListaCitas(
    modifier: Modifier = Modifier,
    citasSeleccionadas: MutableList<Triple<String, String, String>>,
    gestionesViewModel: GestionesViewModel
) {
    val citas by gestionesViewModel.tipoCitas.observeAsState(initial = emptyList())

    val citasTransformadas = citas.map { cita ->
        Triple("${cita.nombre}", "${cita.sesiones} sesión(es)", "${cita.precio}€")
    }

    LazyColumn(modifier = modifier) {
        items(citasTransformadas) { citaTransformada ->
            ItemCita(cita = citaTransformada, citasSeleccionadas = citasSeleccionadas)
        }
    }
}
