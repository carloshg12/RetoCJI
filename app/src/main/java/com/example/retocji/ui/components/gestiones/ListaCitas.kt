package com.example.retocji.ui.screens.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.retocji.ui.viewmodels.GestionesViewModel

@Composable
fun ListaCitas(
    modifier: Modifier,
    citasSeleccionadas: MutableList<Triple<String, String, String>>,
    gestionesViewModel: GestionesViewModel
) {
    val citas = listOf(
        Triple("Asesoría", "Consulta personalizada para resolver tus dudas.", "1 sesión - 15€"),
        Triple("Asesoría online", "Asesoramiento profesional a través de internet.", "1 sesión - 15€"),
        Triple("Declaración de la renta", "Asistencia en la preparación y presentación de tu declaración de impuestos.", "1 sesión - 50€"),
        Triple("Tramitación documentación", "Gestión y tramitación de documentos oficiales.", "2 sesiones - 15€"),
        Triple("Trámites personales en general", "Apoyo en cualquier trámite personal que necesites realizar.", "1 sesión - 15€"),
        Triple("Trámites para sociedades", "Servicios especializados para empresas y sociedades.", "3 sesiones - 30€"),
        Triple("Empadronamiento y similares", "Ayuda con el empadronamiento y trámites similares.", "1 sesión - 15€")
    )

    LazyColumn(modifier = modifier) {
        items(citas) { cita ->
            ItemCita(cita, citasSeleccionadas)
        }
    }
}
