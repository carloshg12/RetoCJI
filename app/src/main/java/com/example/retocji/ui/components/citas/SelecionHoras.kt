import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.retocji.ui.viewmodels.CitasViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeleccionHoras(
    selectedHour: MutableState<String>,
    onHourSelected: (String) -> Unit,
    asesorDeseado: String,
    horas: List<String>?,
    citasViewModel: CitasViewModel
) {
    LazyColumn {
        items(horas ?: emptyList()) { hora ->
            val isSelected = selectedHour.value == hora
            Text(
                text = hora,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Si se hace clic en una hora, se establece como seleccionada
                        if (!isSelected) {
                            selectedHour.value = hora
                            onHourSelected(hora)
                            citasViewModel.setSelectedHour(hora)
                        }
                    }
                    .background(if (isSelected) Color.Yellow else Color.Transparent) // Cambia el color de fondo si está seleccionado
            )
            Divider() // Opcional: Agrega una línea divisoria entre elementos
        }
    }
}




