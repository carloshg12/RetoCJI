package com.example.retocji.ui.components.informacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapaUbicacion(latitud: Double, longitud: Double, titulo: String) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val marker = LatLng(latitud, longitud)
        val context = LocalContext.current
        val mapView = MapView(context).apply {
            onCreate(null)
        }

        AndroidView({ mapView }) { mapView ->
            mapView.getMapAsync { googleMap ->
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15f))
                googleMap.addMarker(
                    MarkerOptions()
                        .position(marker)
                        .title(titulo)
                )
                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
            }
        }
    }
}
