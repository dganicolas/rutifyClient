package com.example.rutifyclient.componentes.casillasDeVerificacion

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.textos.TextoInformativo

@Composable
fun CheckboxConTexto(
    mostrarContrasena: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textId:Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    ) {
        Checkbox(
            checked = mostrarContrasena,
            onCheckedChange = onCheckedChange
        )
        TextoInformativo(textoId = textId)
    }
}
