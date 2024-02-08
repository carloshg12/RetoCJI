package com.example.retocji

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
import com.example.retocji.ui.screens.LoginView
import com.example.retocji.ui.viewmodels.logIn.LoginViewModel
import com.example.retocji.ui.components.Scaffold
import com.example.retocji.ui.viewmodels.CitasViewModel
import com.example.retocji.ui.screens.Bienvenida
import com.example.retocji.ui.viewmodels.logIn.RegistroViewModel
import com.example.retocji.ui.screens.Citas
import com.example.retocji.ui.screens.GestionesScreen
import com.example.retocji.ui.screens.Informacion
import com.example.retocji.ui.screens.logIn.Registro
import com.example.retocji.ui.theme.RetoCJITheme
import com.example.retocji.ui.viewmodels.GestionesViewModel
import com.example.retocji.ui.viewmodels.UserNameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoCJITheme {

                val userNameViewModel : UserNameViewModel = hiltViewModel()
                val isTokenValid by userNameViewModel.isTokenValid.observeAsState()
                userNameViewModel.validateToken()
                val navController = rememberNavController()
                val viewModel: GestionesViewModel = hiltViewModel()
                val citasViewModel: CitasViewModel = hiltViewModel()
                val loginViewModel: LoginViewModel = hiltViewModel()
                val registroViewModel: RegistroViewModel = hiltViewModel()




                NavHost(
                    navController = navController,
                    startDestination = if(isTokenValid == false) "LogIn" else "GeneralInfo"
                ) {
                    composable("GeneralInfo") {

                        Scaffold(navController = navController) {
                            Bienvenida(navController, userNameViewModel)
                        }
                    }
                    composable("Citas") {
                        Scaffold(navController = navController) {
                            Citas(citasViewModel,navController)
                        }
                    }
                    composable("Gestiones") {
                        Scaffold(navController = navController) {
                            GestionesScreen(viewModel)
                        }
                    }
                    composable("SobreNosotros") {
                        Scaffold(navController = navController) {
                            Informacion()
                        }

                    }
                    composable("LogIn") {
                        LoginView(loginViewModel,citasViewModel, navController,userNameViewModel)

                    }
                    composable("Registro") {
                        Registro(navController = navController, registroViewModel,userNameViewModel)
                    }
                }
            }
        }
    }
}