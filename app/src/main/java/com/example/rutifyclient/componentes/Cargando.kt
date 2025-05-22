package com.example.rutifyclient.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun Cargando() {
    Box(
    modifier = Modifier
    .fillMaxSize(), // Ocupa toda la pantalla o contenedor disponible
    contentAlignment = Alignment.Center // Centra el contenido en el medio
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(120.dp),
            strokeWidth = 8.dp
        )
    }
}