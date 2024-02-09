package com.example.retocji.ui.components.bienvenida

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.ui.viewmodels.UserNameViewModel

@Composable
fun CitasRow(userNameViewModel: UserNameViewModel, navController: NavController) {
    Column {
        Text(
            text = "Mis Citas",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                letterSpacing = 0.15.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyRow {
            val citas = userNameViewModel.citasUsuario.value ?: emptyList()
            val citasOrdenadas = citas.sortedWith(compareBy<CitasDTO> { it.horaInicio.split("T")[0] }
                .thenBy { it.horaInicio.split("T").getOrNull(1) ?: "" })

            items(citasOrdenadas) { cita ->
                CardCita(cita)
            }
            item {
                CardSolicitarCita(navController)
            }
        }
    }
}
