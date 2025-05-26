package com.example.rutifyclient.pantalla.cursos

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase

@Composable
fun Cursos(navControlador: NavHostController) {
    PantallaBase(
        viewModel = viewModel(),
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.Cursos) })
    ) { }
}