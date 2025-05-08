package com.example.rutifyclient.componentes.casillasDeVerificacion

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.textos.TextoEnlace
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.espaciadores.SpacerHorizontal


@Composable
fun CheckboxConTextoCondiciones(
    mostrarContrasena: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textId:Int,
    onClickButton: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    ) {
        Checkbox(
            checked = mostrarContrasena,
            onCheckedChange = onCheckedChange
        )
        TextoInformativo(R.string.aceptar)
        SpacerHorizontal(4.dp)
        TextoEnlace(textoId = textId,onClickButton)
    }
}