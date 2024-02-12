package com.example.retocji

import SplashScreen
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retocji.ui.components.Scaffold
import com.example.retocji.ui.screens.Bienvenida
import com.example.retocji.ui.screens.Citas
import com.example.retocji.ui.screens.GestionesScreen
import com.example.retocji.ui.screens.Informacion
import com.example.retocji.ui.screens.LoginView
import com.example.retocji.ui.screens.PantallaContacto
import com.example.retocji.ui.screens.logIn.Registro
import com.example.retocji.ui.theme.RetoCJITheme
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.viewmodels.GestionesViewModel
import com.example.retocji.ui.viewmodels.UserViewModel
import com.example.retocji.ui.viewmodels.logIn.LoginViewModel
import com.example.retocji.ui.viewmodels.logIn.RegistroViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoCJITheme {
                val userViewModel: UserViewModel = hiltViewModel()
                val isTokenValid by userViewModel.isTokenValid.observeAsState()
                userViewModel.validateToken()
                val navController = rememberNavController()
                val viewModel: GestionesViewModel = hiltViewModel()
                val citasViewModel: CitasViewModel = hiltViewModel()
                val loginViewModel: LoginViewModel = hiltViewModel()
                val registroViewModel: RegistroViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = "SplashScreen"
                ) {
                    composable("GeneralInfo") {

                        Scaffold(navController = navController, userViewModel, citasViewModel) {
                            Bienvenida(navController, userViewModel)
                        }
                    }
                    composable("SplashScreen") {
                        SplashScreen(navController, userViewModel)
                    }
                    composable("Citas") {
                        Scaffold(navController = navController, userViewModel, citasViewModel) {
                            Citas(citasViewModel, navController, userViewModel)
                        }
                    }
                    composable("Gestiones") {
                        Scaffold(navController = navController, userViewModel, citasViewModel) {
                            GestionesScreen(viewModel)
                        }
                    }
                    composable("SobreNosotros") {
                        Scaffold(navController = navController, userViewModel, citasViewModel) {
                            Informacion(navController)
                        }

                    }
                    composable("LogIn") {
                        LoginView(loginViewModel, citasViewModel, navController, userViewModel)

                    }
                    composable("Registro") {
                        Registro(
                            navController = navController,
                            registroViewModel,
                            userViewModel,
                            citasViewModel
                        )
                    }
                    composable("Contacto") {
                        Scaffold(navController = navController, userViewModel, citasViewModel) {
                            PantallaContacto()
                        }
                    }
                }
            }
        }
    }
}