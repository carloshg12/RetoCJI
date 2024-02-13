import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retocji.ui.viewmodels.CitasViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeleccionHoras(
    selectedHour: MutableState<String>,
    onHourSelected: (String) -> Unit,
    asesorDeseado: String,
    horas: List<String>?,
    citasViewModel: CitasViewModel
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Hora", modifier = Modifier.widthIn(min = 100.dp))
        Spacer(modifier = Modifier.width(8.dp))


        LazyColumn {
            items(horas ?: emptyList()) { horaInicio ->
                val horaInicioLocalTime = LocalTime.parse(horaInicio, timeFormatter)
                val horaFinLocalTime = horaInicioLocalTime.plusHours(1)
                val horaFin = horaFinLocalTime.format(timeFormatter)

                val isSelected = selectedHour.value == horaInicio
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "$horaInicio - $horaFin",
                        modifier = Modifier
                            .clickable {
                                if (!isSelected) {
                                    selectedHour.value = horaInicio
                                    onHourSelected(horaInicio)
                                    citasViewModel.setSelectedHour(horaInicio)
                                }
                            }
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .padding(bottom = 2.dp, top = 2.dp, end = 8.dp)
                    )
                }
                Divider(
                    modifier = Modifier
                        .padding(start = 42.dp, end = 16.dp)
                )
            }
        }
    }
}
