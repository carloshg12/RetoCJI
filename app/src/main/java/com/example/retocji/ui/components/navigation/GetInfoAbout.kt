package com.example.retocji.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

fun getInfoAbout(): List<NavigationData> {
    return listOf(
        NavigationData(Icons.Filled.Home, "Bienvenido", "GeneralInfo"),
        NavigationData(Icons.Filled.CalendarToday, "Citas", "Citas"),
        NavigationData(Icons.Filled.BusinessCenter, "Gestiones", "Gestiones"),
        NavigationData(Icons.Filled.Info, "Información", "SobreNosotros"),
    )
}
