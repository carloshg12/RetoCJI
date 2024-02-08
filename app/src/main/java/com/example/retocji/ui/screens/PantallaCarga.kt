package com.example.retocji.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retocji.R
import com.example.retocji.ui.viewmodels.UserNameViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController,userNameViewModel: UserNameViewModel){

    val isTokenValid by userNameViewModel.isTokenValid.observeAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicText(
                text = "Bienvenido a I&M Asesores",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }



    LaunchedEffect(key1 = true) {
        delay(2000) //
        navController.navigate(if(isTokenValid == false) "LogIn" else "GeneralInfo") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }
}
