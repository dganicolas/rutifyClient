package com.example.rutifyclient.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rutifyclient.pantalla.Ajustes.Ajustes
import com.example.rutifyclient.pantalla.commons.SplashScreen
import com.example.rutifyclient.pantalla.comunidad.Foro
import com.example.rutifyclient.pantalla.cursos.Cursos
import com.example.rutifyclient.pantalla.login.Login
import com.example.rutifyclient.pantalla.miZona.MiZona
import com.example.rutifyclient.pantalla.register.Registro
import com.example.rutifyclient.pantalla.rutinas.CrearRutinas
import com.example.rutifyclient.pantalla.rutinas.PantallaDetallesRutinas
import com.example.rutifyclient.pantalla.rutinas.Rutinas
import com.example.rutifyclient.pantalla.rutinas.hacerRutina.HacerEjercicioRutina

@Composable
fun AppNavigation() {
    val navControlador = rememberNavController()
    val duracion = 700
    NavHost(
        navController = navControlador,
        startDestination = Rutas.MiZona,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = duracion))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = duracion))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = duracion))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = duracion))
        }
    ) {
        composable(Rutas.Splash) {
            SplashScreen(navControlador)
        }
        composable(Rutas.Rutina) {
            Rutinas(navControlador)
        }
        composable(
            route = Rutas.CrearRutinas
        ) {

            CrearRutinas(navControlador)

        }
        composable(
            route = Rutas.DetallesRutinas,
            arguments = listOf(navArgument("idRutina") { type = NavType.StringType })
        ) { backStackEntry ->
            val idRutina = backStackEntry.arguments?.getString("idRutina") ?: ""
            PantallaDetallesRutinas(idRutina = idRutina, navControlador)
        }

        composable(Rutas.Registro) {
            Registro(navControlador)
        }
        composable(Rutas.MiZona) {
            MiZona(navControlador)
        }
        composable(Rutas.Comunidad) {
            Foro(navControlador)
        }
        composable(Rutas.Ajustes) {
            Ajustes(navControlador)
        }

        composable(
            route = Rutas.HacerEjercicio,
        ) {
            HacerEjercicioRutina(navControlador)
        }

        composable(Rutas.Login) {
            Login(navControlador)
        }
        composable(Rutas.Buscar) {
            //Buscar(navControlador)
        }
        composable(Rutas.DetallesUsuario) {
            //DetallesUsuario(navControlador)
        }

    }
}




