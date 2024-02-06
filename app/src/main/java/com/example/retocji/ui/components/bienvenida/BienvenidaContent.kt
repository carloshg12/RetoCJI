package com.example.retocji.ui.components.bienvenida

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.domain.models.Noticia
import com.example.retocji.ui.viewmodels.UserNameViewModel
import com.google.accompanist.pager.PagerState

@Composable
fun BienvenidaContent(
    navController: NavController,
    userNameViewModel: UserNameViewModel,
    noticiasEjemplo: List<Noticia>,
    pagerState: PagerState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Saludo(userNameViewModel)
        Spacer(modifier = Modifier.height(24.dp))
        SolicitarCita(navController)
        Spacer(modifier = Modifier.height(24.dp))
        NoticiasCarousel(noticiasEjemplo, pagerState)
    }
}
