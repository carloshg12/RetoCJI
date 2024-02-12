package com.example.retocji.ui.components.logIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.retocji.R

@Composable
fun LogoComponent() {
    Image(
        painter = painterResource(id = R.drawable.proyecto2),
        contentDescription = "Logo",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}