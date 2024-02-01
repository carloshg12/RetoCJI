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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retocji.ui.components.NavigationBar
import com.example.retocji.ui.components.TopBar
import com.example.retocji.ui.components.getInfoAbout
import com.example.retocji.ui.screens.GeneralInfo
import com.example.retocji.ui.screens.citas
import com.example.retocji.ui.screens.gestiones
import com.example.retocji.ui.screens.logIn
import com.example.retocji.ui.screens.sobreNosotros
import com.example.retocji.ui.theme.RetoCJITheme

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
                            startDestination = "GeneralInfo"
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
                                logIn()
                            }
                        }
                    }
                }
            }
        }
    }
}