package com.example.rutifyclient.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rutifyclient.pantalla.Ajustes.Ajustes
import com.example.rutifyclient.pantalla.Login
import com.example.rutifyclient.pantalla.MiZona
import com.example.rutifyclient.pantalla.Registro
import com.example.rutifyclient.pantalla.SplashScreen
import com.example.rutifyclient.pantalla.foro.Foro
import com.example.rutifyclient.pantalla.cursos.Cursos
import com.example.rutifyclient.pantalla.rutinas.PantallaDetallesRutinas
import com.example.rutifyclient.pantalla.rutinas.Rutinas
import com.example.rutifyclient.pantalla.barScaffolding.PantallaConBarraInferior
import com.example.rutifyclient.pantalla.rutinas.HacerEjercicioRutina

@Composable
fun AppNavigation() {
    val navControlador = rememberNavController()
    val duracion = 900
    NavHost(
        navController = navControlador,
        startDestination = Rutas.Splash,
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
            PantallaConBarraInferior(navControlador, Rutas.Rutina) {
                Rutinas(Modifier.padding(it), navControlador)
            }
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
        composable(Rutas.Cursos) {
            PantallaConBarraInferior(navControlador, Rutas.Cursos) {
                Cursos()
            }
        }
        composable(Rutas.MiZona) {
            PantallaConBarraInferior(navControlador, Rutas.MiZona) {
                MiZona()
            }
        }
        composable(Rutas.Comunidad) {
            PantallaConBarraInferior(navControlador, Rutas.Comunidad) {
                Foro(Modifier.padding(it), navControlador)
            }
        }
        composable(Rutas.Ajustes) {
            PantallaConBarraInferior(navControlador, Rutas.Ajustes) {
                Ajustes(Modifier.padding(it))
            }
        }

        composable(route = Rutas.HacerEjercicio,
            arguments = listOf(navArgument("ejercicioJson") { type = NavType.StringType })) {
                    backStackEntry ->
                val ejercicioIds = backStackEntry.arguments?.getString("ejercicioJson") ?: ""
                HacerEjercicioRutina(ejercicioIds)
            }

                    composable (Rutas.Login) {
                Login(navControlador)
            }
                    composable (Rutas.Buscar) {
                //Buscar(navControlador)
            }
                    composable (Rutas.DetallesUsuario) {
                //DetallesUsuario(navControlador)
            }

    }
}


