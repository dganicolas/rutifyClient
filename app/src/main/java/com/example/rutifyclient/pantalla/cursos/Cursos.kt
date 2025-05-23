package com.example.rutifyclient.pantalla.cursos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.PantallaBase

@Composable
fun Cursos(navControlador: NavHostController) {
    PantallaBase(
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.Cursos) })
    ) { }
}