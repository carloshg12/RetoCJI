package com.example.retocji.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker

@Composable
fun sobreNosotros(){

        val marker = LatLng(37.7749, -122.4194)
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(mapType = MapType.HYBRID)
        ) {
            Marker(
                position = marker,
                title = "San Francisco",
                snippet = "Population: 776733"
            )
        }
    }


