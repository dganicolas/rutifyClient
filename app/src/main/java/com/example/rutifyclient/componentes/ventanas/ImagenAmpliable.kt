package com.example.rutifyclient.componentes.ventanas

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@Composable
fun ImagenAmpliable(imagenUri: Uri?) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    imagenUri?.let { uri ->
        // Imagen miniatura
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mostrarDialogo = true } // Al hacer clic, mostrar el diálogo
        )
    }

    if (mostrarDialogo) {
        Dialog(onDismissRequest = { mostrarDialogo = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { mostrarDialogo = false }, // Cierra al tocar
                contentAlignment = Alignment.Center
            ) {
                imagenUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}