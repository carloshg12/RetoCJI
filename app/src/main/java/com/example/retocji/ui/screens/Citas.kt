
package com.example.retocji.ui.screens

import android.text.Layout
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.retocji.domain.models.Cita
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun citas() {
    LazyColumn {
        item {
            CitaPersonalizada()
        }
        items(getCitasGenericas()) { cita ->
            CitaGenerica(cita)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaPersonalizada() {
    var diaDeseado by remember { mutableStateOf("") }
    var horaDeseada by remember { mutableStateOf("") }
    var asesorDeseado by remember { mutableStateOf("") }
    var gestionDeseada by remember { mutableStateOf("") }
    var aseores = listOf("Marian", "Ionut")


    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Seleciona tu cita ideal", modifier = Modifier.padding(bottom = 16.dp))

        //secion para poner dia
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = "Dia", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = diaDeseado,
                onValueChange = { diaDeseado = it },
                label = { Text("Seleciona un dia") }
            )
        }

        //secion para poner hora
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = "Hora", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = horaDeseada,
                onValueChange = { horaDeseada = it },
                label = { Text("Seleciona una hora ") }
            )
        }

        //secion para selecionar entre asesores
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = "Gestion", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = gestionDeseada,
                onValueChange = { gestionDeseada = it },
                label = { Text("Especifica que servicio desdeas") }
            )
        }

        //secion para selecionar entre asesores desplegable
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(text = "Asesor", modifier = Modifier.widthIn(min = 100.dp))
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = asesorDeseado,
                onValueChange = { asesorDeseado = it },
                label = { Text("Seleciona un asesor") }
            )
        }


        Button(onClick = {}) {
            Text("Reservar cita")
        }
    }
}

@Composable
fun CitaGenerica(cita: Cita) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dia: ${cita.dia}")
        Text(text = "Hora: ${cita.inicio} - ${cita.fin}")
        Text(text = "Asesor: ${cita.asesor}")
        Text(text = "Gestion: ${cita.gestion}")
        Button(onClick = {}) {
            Text("Reservar cita")
        }
    }
}

fun getCitasGenericas(): List<Cita> {
    return listOf(
        Cita(1, "Lunes", "8:00", "9:00", true, "Marian", "Gestion de impuestos"),
        Cita(2, "Martes", "16:00", "17:00", true, "Ionut", "Divoricio"),
        Cita(3, "Miercoles", "10:00", "11:00", true, "Marian", "Renta"),
        Cita(4, "Jueves", "8:00", "9:00", true, "Ionut", "Renta"),
        Cita(5, "Viernes", "16:00", "17:00", true, "Marian", "Renta"),
        Cita(6, "Domingo", "10:00", "11:00", true, "Ionut", "Divoricio"),
        Cita(7, "Lunes", "8:00", "9:00", true, "Marian", "Gestion de impuestos")
    )
}


