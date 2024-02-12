package com.example.retocji.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.ui.components.informacion.DescripcionEmpresa
import com.example.retocji.ui.components.informacion.Encabezado
import com.example.retocji.ui.components.informacion.InformacionContacto
import com.example.retocji.ui.components.informacion.MapaUbicacion

@Composable
fun Informacion(navController: NavController) {

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
        ) {
            Encabezado("Sobre Nosotros")
            Spacer(modifier = Modifier.height(12.dp))
            DescripcionEmpresa("Somos una empresa dedicada a ofrecer servicios de asesorías en diversas áreas, brindando soluciones integrales y personalizadas a nuestros clientes. Nos esforzamos por proporcionar un servicio de calidad y establecer relaciones duraderas basadas en la confianza mutua.")
            InformacionContacto(navController, "+34 645 17 24 50", "imasesorescji@gmail.com")
            MapaUbicacion(39.9864, -0.0513, "I&M Asesores")
        }
    }
}
