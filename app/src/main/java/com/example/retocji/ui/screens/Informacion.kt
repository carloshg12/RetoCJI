package com.example.retocji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.components.informacion.DescripcionEmpresa
import com.example.retocji.ui.components.informacion.Encabezado
import com.example.retocji.ui.components.informacion.InformacionContacto
import com.example.retocji.ui.components.informacion.MapaUbicacion

@Composable
fun informacion() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Encabezado("Sobre Nosotros")
        DescripcionEmpresa("Somos una empresa dedicada a ofrecer servicios de asesorías en diversas áreas, brindando soluciones integrales y personalizadas a nuestros clientes. Nos esforzamos por proporcionar un servicio de calidad y establecer relaciones duraderas basadas en la confianza mutua.")
        InformacionContacto("+34 645 17 24 50", "ionut@imasesores.com")
        MapaUbicacion(39.9864, -0.0513, "I&M Asesores")
    }
}