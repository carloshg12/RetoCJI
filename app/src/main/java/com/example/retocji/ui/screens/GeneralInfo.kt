package com.example.retocji.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

    @Composable
    fun GeneralInfo(navController: NavController, citasViewModel: CitasViewModel){

        Text(text = "Hola ${citasViewModel.obtenerTokenDesdeSharedPreferences()}")
    }
