package com.example.retocji.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.retocji.ui.components.citas.CitaPersonalizada
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.UserViewModel


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Citas(
    citasViewModel: CitasViewModel,
    navController: NavController,
    userViewModel: UserViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    val asesorDeseado by citasViewModel.asesorDeseado.collectAsState()

    val horas by citasViewModel.horasDisponibles.collectAsState()
    val asesores by citasViewModel.gestoresLiveData.observeAsState(initial = emptyList())
    val gestiones by citasViewModel.nombresTipoCitas.collectAsState()

    val selectedHour by citasViewModel.selectedHour.collectAsState()


    Column {

            CitaPersonalizada(
                asesorDeseado = asesorDeseado,
                selectedHour = mutableStateOf(selectedHour),
                asesores = asesores,
                gestiones = gestiones,
                showDialog = mutableStateOf(showDialog),
                datePickerState = rememberDatePickerState(),
                citasViewModel,
                horas,
                userViewModel)
    }
}
