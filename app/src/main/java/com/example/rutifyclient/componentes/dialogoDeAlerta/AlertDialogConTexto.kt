package com.example.rutifyclient.componentes.dialogoDeAlerta

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo

@Composable
fun AlertDialogConTexto(
    @StringRes titulo: Int,
    @StringRes mensaje: Int,
    correo:String,
    @StringRes textoId: Int = R.string.enviar,
    onValueChanged: (String) ->Unit,
    onConfirm: () -> Unit,
    denegar: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            ButtonPrincipal(textoId,{onConfirm()})
        },
        title = { TextoSubtitulo(titulo) },
        text = {
            Column {
                TextoInformativo(mensaje)
                SpacerVertical(6.dp)
                CampoTexto(
                    value = correo,
                    onValueChange = { onValueChanged(it) },
                    textoIdLabel = R.string.correo,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        dismissButton = {
            ButtonSecundario(R.string.cancelar, onClick = {
                denegar()
            })
        },
        containerColor = colorScheme.surface.copy(alpha = 0.9f),
        textContentColor = colorScheme.onSurface
    )
}