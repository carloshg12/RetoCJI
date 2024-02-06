import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun seleccionHoras(
    selectedHour: MutableState<String>,
    onHourSelected: (String) -> Unit,
    asesorDeseado: String,
    horas: List<String>?
) {
    LazyColumn {
        items(horas ?: emptyList()) { hora ->
            Text(
                text = hora,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedHour.value = hora
                        onHourSelected(hora)
                    }
            )
            Divider() // Opcional: Agrega una línea divisoria entre elementos
        }
    }
}



