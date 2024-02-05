package com.example.retocji.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

fun getInfoAbout(): List<Navigation_Data> {
    return listOf(
        Navigation_Data(Icons.Filled.Home, "Bienvenido", "GeneralInfo"),
        Navigation_Data(Icons.Filled.CalendarToday, "Citas", "Citas"),
        Navigation_Data(Icons.Filled.BusinessCenter, "Gestiones", "Gestiones"),
        Navigation_Data(Icons.Filled.Info, "Información", "SobreNosotros"),
    )
}
