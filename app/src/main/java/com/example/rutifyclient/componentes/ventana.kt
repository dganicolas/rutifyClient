package com.example.rutifyclient.componentes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Ventana(mensaje: String, titulo: String, accion: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = accion,
            confirmButton = {
                Button(onClick = accion) {
                    Text("Aceptar")
                }
            },
            title = { Text(titulo) },
            text = { Text(mensaje) },
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            textContentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun previewVentana() {
    Ventana("prueba","prueba titulo",{})
}