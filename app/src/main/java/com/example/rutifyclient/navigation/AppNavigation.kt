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
import com.example.rutifyclient.pantalla.comunidad.buscador.Buscador
import com.example.rutifyclient.pantalla.comunidad.comentarios.DetallesComentario
import com.example.rutifyclient.pantalla.comunidad.perfil
import com.example.rutifyclient.pantalla.estadisticas.Estadisticas
import com.example.rutifyclient.pantalla.login.Login
import com.example.rutifyclient.pantalla.miZona.MiZona
import com.example.rutifyclient.pantalla.register.Registro
import com.example.rutifyclient.pantalla.rutinas.Actividad
import com.example.rutifyclient.pantalla.rutinas.CrearRutinas
import com.example.rutifyclient.pantalla.rutinas.PantallaBusquedaRutinasOnline
import com.example.rutifyclient.pantalla.rutinas.PantallaDetallesRutinas
import com.example.rutifyclient.pantalla.rutinas.Rutinas
import com.example.rutifyclient.pantalla.rutinas.hacerRutina.HacerEjercicioRutina
import com.example.rutifyclient.pantalla.tienda.Tienda
import com.example.rutifyclient.pantalla.zonaAdmin.AdministrarComentarios
import com.example.rutifyclient.pantalla.zonaAdmin.AdministrarUsuarios
import com.example.rutifyclient.viewModel.ajustes.SettingsViewModel

@Composable
fun AppNavigation(settingsViewModel: SettingsViewModel) {
    val navControlador = rememberNavController()
    val duracion = 700
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
            Registro(navControlador = navControlador)
        }
        composable(Rutas.MiZona,
            arguments = listOf(navArgument("idFirebase") { type = NavType.StringType })) {
            MiZona(navControlador)
        }
        composable(Rutas.perfil,
            arguments = listOf(navArgument("idFirebase") { type = NavType.StringType })) {

                backStackEntry ->
            val idFirebase: String = backStackEntry.arguments?.getString("idFirebase") ?: ""
            perfil(navControlador,idFirebase)
        }
        composable(Rutas.Comunidad) {
            Foro(navControlador)
        }

        composable(Rutas.administrarComentarios) {
            AdministrarComentarios(navControlador)
        }

        composable(Rutas.buscarRutinas) {
            PantallaBusquedaRutinasOnline(navControlador)
        }

        composable(Rutas.Ajustes) {
            Ajustes(navControlador,settingsViewModel)
        }

        composable(Rutas.BuscarComentarios) {
            Buscador(navControlador)
        }

        composable(Rutas.Estadisticas) {
            Estadisticas(navControlador)
        }

        composable(Rutas.administrarUsuarios) {
            AdministrarUsuarios(navControlador)
        }

        composable(Rutas.rutinasFavoritas,
            arguments = listOf(navArgument("idFirebase") { type = NavType.StringType })
        ) { backStackEntry ->
            val idRutina: String = backStackEntry.arguments?.getString("idFirebase") ?: ""
            Actividad(navControlador,idRutina)
        }

        composable(
            route = Rutas.HacerEjercicio,
        ) {
            HacerEjercicioRutina(navControlador)
        }

        composable(Rutas.Login) {
            Login(navControlador)
        }

        composable(Rutas.Tienda) {
            Tienda(navControlador)
        }

        composable(Rutas.DetallesComentario,
            arguments = listOf(navArgument("idComentario") { type = NavType.StringType })) {
                backStackEntry ->
            val urlComentario: String = backStackEntry.arguments?.getString("idComentario") ?: ""
            DetallesComentario(navControlador,urlComentario)
        }

    }
}








