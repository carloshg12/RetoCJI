package com.example.retocji.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.*
import androidx.navigation.NavController
import com.example.retocji.R
import com.example.retocji.domain.models.Noticia
import com.example.retocji.ui.components.bienvenida.BienvenidaContent
import com.example.retocji.ui.viewmodels.UserNameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bienvenida(navController: NavController, userNameViewModel: UserNameViewModel) {
    val pagerState = rememberPagerState()

    val noticiasEjemplo = listOf(
        Noticia(
            titulo = "Nuevas Reformas Tributarias",
            resumen = "Descubre cómo las últimas reformas afectarán a tu negocio.",
            imagenId = R.drawable.reformas
        ),
        Noticia(
            titulo = "Consejos para la Declaración de la Renta",
            resumen = "Evita errores comunes y maximiza tu devolución con nuestros consejos expertos.",
            imagenId = R.drawable.renta
        ),
    )

    Scaffold (){
        BienvenidaContent(navController, userNameViewModel, noticiasEjemplo, pagerState)
    }

}