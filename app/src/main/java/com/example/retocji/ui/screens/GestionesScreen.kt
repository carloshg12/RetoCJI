package com.example.retocji.ui.screens

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retocji.ui.viewmodels.GestionesViewModel
import dagger.hilt.android.AndroidEntryPoint

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

@Composable
fun ListaCitas(modifier: Modifier, citasSeleccionadas: MutableList<Triple<String, String, String>>, gestionesViewModel: GestionesViewModel) {
    val citas = listOf(
        Triple("Asesoría", "Consulta personalizada para resolver tus dudas.", "1 sesión - 15€"),
        Triple("Asesoría online", "Asesoramiento profesional a través de internet.", "1 sesión - 15€"),
        Triple("Declaración de la renta", "Asistencia en la preparación y presentación de tu declaración de impuestos.", "1 sesión - 50€"),
        Triple("Tramitación documentación", "Gestión y tramitación de documentos oficiales.", "2 sesiónes - 15€"),
        Triple("Trámites personales en general", "Apoyo en cualquier trámite personal que necesites realizar.", "1 sesión - 15€"),
        Triple("Trámites para sociedades", "Servicios especializados para empresas y sociedades.", "3 sesiones- 30€"),
        Triple("Empadronamiento y similares", "Ayuda con el empadronamiento y trámites similares.", "1 sesión - 15€")
    )

    LazyColumn(modifier = modifier) {
        items(citas) { cita ->
            ItemCita(cita, citasSeleccionadas)
        }
    }
}

@Composable
fun ItemCita(cita: Triple<String, String, String>, citasSeleccionadas: MutableList<Triple<String, String, String>>) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).clickable { expanded = !expanded }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = cita.first,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = cita.second,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = cita.third,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF007F0E)
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        if (cita !in citasSeleccionadas) citasSeleccionadas.add(cita)
                        else citasSeleccionadas.remove(cita)
                    }) {
                        Text(if (cita !in citasSeleccionadas) "Añadir" else "Eliminar")
                        Icon(if (cita !in citasSeleccionadas) Icons.Filled.Add else Icons.Filled.Remove, contentDescription = if (cita !in citasSeleccionadas) "Añadir" else "Eliminar")
                    }
                }
            }
        }
    }
}

@Composable
fun CarritoCitas(citasSeleccionadas: List<Triple<String, String, String>>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Citas Seleccionadas:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (citasSeleccionadas.isNotEmpty()) {
            citasSeleccionadas.forEach { cita ->
                Text("- ${cita.first}: ${cita.third}", modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))
            }
        } else {
            Text("No hay citas seleccionadas.")
        }
    }
}

@Composable
fun BotonImprimirInformacion(citasSeleccionadas: List<Triple<String, String, String>>, gestionesViewModel: GestionesViewModel) {
    val context = LocalContext.current
    Button(
        onClick = { gestionesViewModel.generarPDF(context, citasSeleccionadas) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Imprimir Información")
    }
}