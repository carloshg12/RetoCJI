package com.example.retocji.ui.components.bienvenida

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.pager.*
import com.example.retocji.domain.models.Noticia
import kotlinx.coroutines.delay

@Composable
fun NoticiasCarousel(noticiasEjemplo: List<Noticia>, pagerState: PagerState) {
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(3000) // Cambia cada 3 segundos
            val nextPage = (pagerState.currentPage + 1) % noticiasEjemplo.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        count = noticiasEjemplo.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // Altura total de la tarjeta
    ) { page ->
        val noticia = noticiasEjemplo[page]
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // Altura total de la tarjeta
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Elevación aumentada para más profundidad
        ) {
            Column {
                Image(
                    painter = painterResource(id = noticia.imagenId),
                    contentDescription = "Imagen de noticia",
                    modifier = Modifier
                        .weight(0.5f) // Asigna un peso para ocupar una parte proporcional de la altura de la tarjeta
                        .fillMaxWidth(), // La imagen ocupará todo el ancho disponible
                    contentScale = ContentScale.Crop // Asegura que la imagen cubra todo el espacio disponible
                )
                Column(
                    modifier = Modifier
                        .weight(0.5f) // Asigna un peso para que el texto ocupe la parte restante de la altura
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)) // Fondo con transparencia
                        .fillMaxWidth() // El texto ocupará todo el ancho disponible
                        .padding(16.dp), // Aumento del relleno para el texto
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = noticia.titulo,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), // Texto más grande y en negrita
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp) // Espaciado aumentado entre el título y el resumen
                    )
                    Text(
                        text = noticia.resumen,
                        style = MaterialTheme.typography.bodyLarge, // Texto más grande para mejor legibilidad
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp) // Espaciado aumentado al final del resumen
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth() // Asegura que el Box ocupe todo el ancho disponible
            .padding(16.dp) // Agrega un padding alrededor del indicador para posicionarlo correctamente
    ) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.Center), // Centra el indicador horizontalmente dentro del Box
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
    }

}
