package com.example.rutifyclient.componentes.dialogoDeAlerta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonAlerta
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.deslizables.RatingBar
import com.example.rutifyclient.componentes.textos.TextoBotones
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo

@Composable
fun PuntuarRutinaDialog(
    rating: Float = 0f,
    onRatingChanged: (Float) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { TextoSubtitulo(R.string.puntuarRutina) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextoInformativo(R.string.mensajePuntuarRutina)
                Spacer(modifier = Modifier.height(12.dp))
                RatingBar(rating = rating, onRatingChanged = { onRatingChanged(it) })
            }
        },
        confirmButton = {
            ButtonPrincipal(R.string.enviar,onClick = { onConfirm() })
        },
        dismissButton = {
            ButtonAlerta(R.string.cancelar,onClick = { onDismiss() })
        }
    )
}
