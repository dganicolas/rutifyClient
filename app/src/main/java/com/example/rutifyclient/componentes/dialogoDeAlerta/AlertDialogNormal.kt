package com.example.rutifyclient.componentes.dialogoDeAlerta

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo

@Composable
fun AlertDialogNormal(@StringRes titulo: Int,
                @StringRes mensaje: Int,
                accion: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = accion,
            confirmButton = {
                ButtonPrincipal(R.string.aceptar,onClick = accion)
            },
            title = { TextoSubtitulo(titulo) },
            text = { TextoInformativo(mensaje) },
            containerColor = colorScheme.surface,
            textContentColor = colorScheme.onSurface
        )
    }
}