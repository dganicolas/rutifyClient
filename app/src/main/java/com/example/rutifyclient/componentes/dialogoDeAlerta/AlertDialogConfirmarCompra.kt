package com.example.rutifyclient.componentes.dialogoDeAlerta

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonAlerta
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo

@Composable
fun AlertDialogConfirmarCompra(
    titulo: Int,
    mensaje: Int,
    aceptar: () -> Unit,
    denegar: () -> Unit,
    aceptarTexto: Int = R.string.aceptar
) {
    AlertDialog(
        onDismissRequest = denegar,
        confirmButton = {
            ButtonPrincipal(aceptarTexto, onClick = aceptar)
        },
        dismissButton = {
            ButtonSecundario(R.string.cancelar, onClick = {
                denegar()
            })
        },
        title = { TextoSubtitulo(titulo) },
        text = { TextoInformativo(mensaje) },
        containerColor = colorScheme.surface,
        textContentColor = colorScheme.onSurface
    )
}