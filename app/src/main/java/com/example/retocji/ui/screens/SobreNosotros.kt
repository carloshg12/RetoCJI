package com.example.retocji.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun sobreNosotros() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        Text(
            text = "Sobre Nosotros",
            fontSize = 28.sp,
            style = MaterialTheme.typography.bodyMedium
        )

        // Descripción de la empresa
        Text(
            text = "Somos una empresa dedicada a ofrecer servicios de asesorías en diversas áreas, brindando soluciones integrales y personalizadas a nuestros clientes. Nos esforzamos por proporcionar un servicio de calidad y establecer relaciones duraderas basadas en la confianza mutua.",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        // Información de contacto
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = "Contacto",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Teléfono: +34 645 17 24 50",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Correo electrónico: ionut@imasesores.com",
                fontSize = 16.sp,
            )
        }

        // Mapa
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val marker = LatLng(39.9864, -0.0513)
            val context = LocalContext.current
            val mapView = MapView(context).apply {
                // Necesario para que el mapa se muestre inmediatamente
                onCreate(null)
            }

            AndroidView({ mapView }) { mapView ->
                mapView.getMapAsync { googleMap ->
                    // Mover la cámara a la ubicación del marcador con un zoom específico
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15f))

                    // Añadir un marcador en la ubicación
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(marker)
                            .title("I&M Asesores")
                    )

                    // Configurar otras opciones del mapa según sea necesario
                    googleMap.uiSettings.isZoomControlsEnabled = true
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                }
            }
        }
    }
}

