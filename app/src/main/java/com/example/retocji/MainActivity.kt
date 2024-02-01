package com.example.retocji

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.LoginView
import com.example.login.LoginViewModel
import com.example.retocji.ui.components.NavigationBar
import com.example.retocji.ui.components.TopBar
import com.example.retocji.ui.components.getInfoAbout
import com.example.retocji.ui.screens.AuthViewModel
import com.example.retocji.ui.screens.GeneralInfo
import com.example.retocji.ui.screens.SharedPreferencesManager
import com.example.retocji.ui.screens.citas
import com.example.retocji.ui.screens.gestiones
import com.example.retocji.ui.screens.sobreNosotros
import com.example.retocji.ui.theme.RetoCJITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoCJITheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar(navController, "I&M Asesores") },
                    bottomBar = {
                        NavigationBar(getInfoAbout(), navController)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "LogIn"
                        ) {
                            composable("GeneralInfo") {
                                GeneralInfo(navController)
                            }
                            composable("Citas") {
                                citas()
                            }
                            composable("Gestiones") {
                                gestiones()
                            }
                            composable("SobreNosotros") {
                                sobreNosotros()
                            }
                            composable("LogIn") {

                                val loginViewModel:LoginViewModel = hiltViewModel()

                                LoginView().login(loginViewModel)

                            }
                        }
                    }
                }
            }
        }
    }
}