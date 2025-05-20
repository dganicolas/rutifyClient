package com.example.rutifyclient.componentes.ventanas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ventanaModal(
    contenido: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background.copy(alpha = 0.5f)) // Fondo oscuro semitransparente
    ) {
        Box( // Contenedor interno con padding
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            contenido()
        }
    }
}