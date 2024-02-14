import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.retocji.R
import com.example.retocji.ui.viewmodels.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, userViewModel: UserViewModel) {
    val isTokenValid by userViewModel.isTokenValid.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var startAnimation by remember { mutableStateOf(false) }
            val alphaAnim by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
                label = ""
            )

            LaunchedEffect(key1 = true) {
                startAnimation = true
                delay(1000)
                navController.navigate(if (isTokenValid == false || isTokenValid == null) "LogIn" else "GeneralInfo") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.proyecto2),
                contentDescription = "Logotipo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .alpha(alphaAnim)
            )

            Text(
                text = "Bienvenido a I&M Asesores",
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(alphaAnim),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Cargando...",
                modifier = Modifier
                    .padding(top = 12.dp)
                    .alpha(alphaAnim),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}