package com.example.rutifyclient.pantalla.barScaffolding

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal

@Composable
fun PantallaConBarraInferiorConBoton(
    navController: NavController,
    rutaActual: String,
    onCrearRutinaClick: () -> Unit,
    contenido: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBarAbajoPrincipal(navController, rutaActual)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCrearRutinaClick,
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Crear rutina")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        contenido(innerPadding)
    }
}

