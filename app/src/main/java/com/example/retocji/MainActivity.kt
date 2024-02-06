package com.example.retocji

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retocji.ui.screens.logIn.LoginView
import com.example.retocji.ui.viewmodels.logIn.LoginViewModel
import com.example.retocji.ui.components.scaffold
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.screens.Bienvenida
import com.example.retocji.ui.screens.logIn.Registro
import com.example.retocji.ui.viewmodels.logIn.RegistroViewModel
import com.example.retocji.ui.screens.citas
import com.example.retocji.ui.screens.GestionesScreen
import com.example.retocji.ui.screens.Informacion
import com.example.retocji.ui.theme.RetoCJITheme
import com.example.retocji.ui.viewmodels.GestionesViewModel
import com.example.retocji.ui.viewmodels.UserNameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoCJITheme {
                val navController = rememberNavController()
                val viewModel: GestionesViewModel = hiltViewModel()
                val citasViewModel: CitasViewModel = hiltViewModel()
                val loginViewModel: LoginViewModel = hiltViewModel()

                val userNameViewModel : UserNameViewModel = hiltViewModel()
                NavHost(
                    navController = navController,
                    startDestination = "LogIn"
                ) {
                    composable("GeneralInfo") {

                        scaffold(navController = navController) {
                            Bienvenida(navController, userNameViewModel)
                        }
                    }
                    composable("Citas") {
                        scaffold(navController = navController) {
                            citas(citasViewModel,navController)
                        }
                    }
                    composable("Gestiones") {
                        scaffold(navController = navController) {
                            GestionesScreen(viewModel)
                        }
                    }
                    composable("SobreNosotros") {
                        scaffold(navController = navController) {
                            Informacion()
                        }

                    }
                    composable("LogIn") {
                        LoginView().login(loginViewModel,citasViewModel, navController)

                    }
                    composable("Registro") {
                        val registroViewModel: RegistroViewModel = hiltViewModel()
                        Registro(navController = navController, registroViewModel)
                    }
                }
            }
        }
    }
}