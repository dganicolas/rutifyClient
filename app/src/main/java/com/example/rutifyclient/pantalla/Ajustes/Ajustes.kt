package com.example.rutifyclient.pantalla.Ajustes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.botones.ButtonAlerta
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase

@Composable
fun Ajustes(navControlador: NavHostController) {
    PantallaBase(
        viewModel = viewModel(),
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.Ajustes) })
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ButtonPrincipal(
                R.string.zonaAdmin,
                onClick = {
                    navControlador.navigate(Rutas.zonaAdmin)
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .size(60.dp)
            )
            ButtonAlerta(
                textoId = R.string.eliminar_cuenta,
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .size(60.dp)
            )
        }
    }
}