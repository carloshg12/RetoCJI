package com.example.retocji.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.reflect.Modifier

@Composable
fun sobreNosotros(){
    /*
    var descripcion by remember { mutableStateOf("Aquí va la descripción") }

    Column {
        Text(text = "Sobre nosotros")
        Text(text = descripcion)
        AndroidView({ context ->
            MapView(context).apply {
                onCreate(Bundle())
                getMapAsync { googleMap ->
                    googleMap.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker"))
                }
            }
        })
    }*/
}