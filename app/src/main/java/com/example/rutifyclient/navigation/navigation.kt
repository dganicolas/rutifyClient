package com.example.rutifyclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rutifyclient.pantalla.Bienvenida
import com.example.rutifyclient.pantalla.Login

@Composable
fun AppNavigation(modifier: Modifier) {
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = "login") {
        composable("bienvenida") {
            Bienvenida()
        }
        composable("login") {
            Login()
        }
    }
}