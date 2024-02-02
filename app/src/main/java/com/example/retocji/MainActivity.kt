package com.example.retocji

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.LoginView
import com.example.login.LoginViewModel
import com.example.retocji.ui.components.Navigation_Data
import com.example.retocji.ui.components.TopBar
import com.example.retocji.ui.components.getInfoAbout
import com.example.retocji.ui.screens.CitasViewModel
import com.example.retocji.ui.screens.GeneralInfo
import com.example.retocji.ui.screens.Registro
import com.example.retocji.ui.screens.RegistroViewModel
import com.example.retocji.ui.screens.citas
import com.example.retocji.ui.screens.GestionesScreen
import com.example.retocji.ui.screens.sobreNosotros
import com.example.retocji.ui.theme.RetoCJITheme
import com.example.retocji.ui.viewmodels.GestionesViewModel
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

                NavHost(
                            navController = navController,
                            startDestination = "GeneralInfo"
                        ) {
                            composable("GeneralInfo") {
                                val citasViewModel: CitasViewModel = hiltViewModel()
                                scaffold(navController = navController) {
                                    GeneralInfo(navController,citasViewModel)
                                }
                            }
                            composable("Citas") {
                                scaffold(navController = navController) {
                                    citas()
                                }
                            }
                            composable("Gestiones") {
                                scaffold(navController = navController) {
                                    GestionesScreen(viewModel)
                                }
                            }
                            composable("SobreNosotros") {
                                sobreNosotros()
                            }
                            composable("LogIn") {

                                val loginViewModel:LoginViewModel = hiltViewModel()

                                LoginView().login(loginViewModel,navController)

                            }
                            composable("Registro") {
                                val registroViewModel:RegistroViewModel = hiltViewModel()
                                Registro(navController = navController,registroViewModel)
                            }
                        }
                    }
                }
            }
        }

@Composable
fun scaffold(navController: NavController, content: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController, "I&M Asesores")
        },
        bottomBar = {
            NavigationBar(navController, getInfoAbout())
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
            content()
        }
    }
}

@Composable
fun NavigationBar(navController: NavController, items: List<Navigation_Data>) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    BottomNavigation (backgroundColor = Color.LightGray){
        items.forEach { item ->
            BottomNavigationItem(
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == item.route,
                icon = { Icon(imageVector = item.icon, contentDescription = item.text) },
                label = { Text(text = item.text) },
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}


