package com.example.retocji.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import androidx.navigation.NavController
import com.example.retocji.R
import com.example.retocji.domain.models.bienvenida.Noticia
import com.example.retocji.ui.components.bienvenida.CitasRow
import com.example.retocji.ui.components.bienvenida.NoticiasCarousel
import com.example.retocji.ui.components.bienvenida.Saludo
import com.example.retocji.ui.viewmodels.UserNameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bienvenida(navController: NavController, userNameViewModel: UserNameViewModel) {
    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()


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

    Scaffold() {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 2.dp , bottom = 12.dp , start = 24.dp, end = 24.dp)
            ) {
                Saludo(userNameViewModel)
                Spacer(modifier = Modifier.height(6.dp))
                CitasRow(userNameViewModel, navController)
                Spacer(modifier = Modifier.height(24.dp))
                NoticiasCarousel(noticiasEjemplo, pagerState)
            }
        }
    }

}