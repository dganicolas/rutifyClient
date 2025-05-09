package com.example.rutifyclient.pantalla.barScaffolding

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal

@Composable
fun PantallaConBarraInferior(
    navController: NavController,
    rutaActual: String,
    contenido: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBarAbajoPrincipal(navController, rutaActual)
        }
    ) { innerPadding ->
        contenido(innerPadding)
    }
}