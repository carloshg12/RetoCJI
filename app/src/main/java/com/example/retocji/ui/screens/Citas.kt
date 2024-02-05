package com.example.retocji.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.retocji.ui.components.citas.CitaPersonalizada
import com.example.retocji.ui.viewmodels.CitasViewModel


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun citas(citasViewModel: CitasViewModel, navController: NavController) {
    var expandedAsesores by remember { mutableStateOf(false) }
    var asesorDeseado by remember { mutableStateOf("") }
    var expandedGestiones by remember { mutableStateOf(false) }
    var gestionDeseada by remember { mutableStateOf("") }
    var expandedHour by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val asesores by citasViewModel.gestoresLiveData.observeAsState(initial = emptyList())



    LazyColumn {
        item {
            CitaPersonalizada(
                expandedAsesores = mutableStateOf(expandedAsesores),
                asesorDeseado = mutableStateOf(asesorDeseado),
                expandedGestiones = mutableStateOf(expandedGestiones),
                gestionDeseada = mutableStateOf(gestionDeseada),
                expandedHour = mutableStateOf(expandedHour),
                selectedHour = mutableStateOf(selectedHour),
                asesores = asesores,
                gestiones = listOf("Gestion 1", "Gestion 2", "Gestion 3"),
                showDialog = mutableStateOf(showDialog),
                datePickerState = rememberDatePickerState(),
            )
        }
    }
}
