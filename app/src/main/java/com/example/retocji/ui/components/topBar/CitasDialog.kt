import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.ui.viewmodels.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CitasDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    listaCitas: List<CitasDTO>?,
    userViewModel: UserViewModel
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Información de Citas", Modifier.weight(1f))
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Black)
                    }
                }
            },
            text = {
                CitasDialogContent(listaCitas, userViewModel)
            },
            confirmButton = {}
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun CitasDialogContent(listaCitas: List<CitasDTO>?, userViewModel: UserViewModel) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        listaCitas?.forEach { cita ->
            val fechaHoraInicio =
                LocalDateTime.parse(cita.horaInicio, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val fechaFormateada = fechaHoraInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val horaFormateada = fechaHoraInicio.format(DateTimeFormatter.ofPattern("HH:mm"))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Día: ") }
                    append("$fechaFormateada\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Hora: ") }
                    append("$horaFormateada\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Tipo: ") }
                    append("${cita.tipoCita.nombre}\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Gestor: ") }
                    append("${cita.gestor.name}\n")
                }, modifier = Modifier.padding(8.dp))

                Icon(
                    Icons.Default.Close,
                    contentDescription = "Borrar cita",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = {})
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    Log.e(
                                        "FechaFormateada",
                                        cita.horaInicio
                                    )
                                    userViewModel.borrarCitaPorUsuario(
                                        cita.horaInicio
                                    )
                                }
                            )
                        }
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
