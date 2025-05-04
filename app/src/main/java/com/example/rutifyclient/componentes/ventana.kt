package com.example.rutifyclient.componentes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R

@Composable
fun Ventana(@StringRes titulo: Int,
            @StringRes mensaje: Int,
            accion: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = accion,
            confirmButton = {
                Button(onClick = accion) {
                    Text("Aceptar")
                }
            },
            title = { TextoSubtitulo(titulo) },
            text = { TextoInformativo(mensaje) },
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            textContentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun VentanaFieldText(
    @StringRes titulo: Int,
    @StringRes mensaje: Int,
    correo:String,
    @StringRes textoId: Int = R.string.enviar,
    onValueChanged: (String) ->Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { /* No hacemos nada si se cierra el diálogo */ },
        confirmButton = {
            BotonPrincipal(textoId,{onConfirm()})
        },
        title = { TextoSubtitulo(titulo) },
        text = {
            Column {
                TextoInformativo(mensaje)
                Spacer(modifier = Modifier.height(16.dp))  // Espacio entre mensaje y campo de texto
                TextField(
                    value = correo,
                    onValueChange = { onValueChanged(it) },  // Actualiza el valor del campo de texto
                    label = { Text("Escribe aquí...") },  // Etiqueta para el campo de texto
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Preview
@Composable
fun previewVentana() {
    Ventana(R.string.sesion_iniciada,R.string.bienvenida,{})
}
@Preview
@Composable
fun previewVentana1() {
    VentanaFieldText(
        titulo = R.string.recuperar_contrasena,
        mensaje = R.string.indique_correo,
        correo = "c",
        onConfirm = {},
        onValueChanged = {})
}