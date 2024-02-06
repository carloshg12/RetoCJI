import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.retocji.domain.repositories.CitasDTO
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun seleccionHoras(
    selectedHour: MutableState<String>,
    onHourSelected: (String) -> Unit,
    asesorDeseado: String,
    horas: List<String>?
) {
    var expanded by remember { mutableStateOf(false) }
    // Asumiendo que 'horas' ya contiene las horas disponibles,
    // y que 'selectedHour.value' es la hora actualmente seleccionada.

    Column {
        TextField(
            value = selectedHour.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    Modifier.clickable { expanded = !expanded }
                )
            },
            label = { Text("Selecciona una hora") }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            horas?.forEach { hora ->
                DropdownMenuItem(onClick = {
                    selectedHour.value = hora
                    onHourSelected(hora)
                    expanded = false
                }, text = { Text(text = hora)})
            }
        }
    }
}



