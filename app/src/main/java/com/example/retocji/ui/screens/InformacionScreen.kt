package com.example.retocji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.components.informacion.*

@Composable
fun Informacion() {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp , bottom = 12.dp , start = 24.dp, end = 24.dp)
        ) {
            Encabezado("Sobre Nosotros")
            Spacer(modifier = Modifier.height(12.dp))
            DescripcionEmpresa("Somos una empresa dedicada a ofrecer servicios de asesorías en diversas áreas, brindando soluciones integrales y personalizadas a nuestros clientes. Nos esforzamos por proporcionar un servicio de calidad y establecer relaciones duraderas basadas en la confianza mutua.")
            InformacionContacto("+34 645 17 24 50", "ionut@imasesores.com")
            MapaUbicacion(39.9864, -0.0513, "I&M Asesores")
        }
    }
}
