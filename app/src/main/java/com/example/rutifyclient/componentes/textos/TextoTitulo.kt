package com.example.rutifyclient.componentes.textos

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun TextoTitulo(textoId: Int,vararg args:Any) {
    Text(
        text = if (args.isEmpty()) {
            stringResource(id = textoId)
        } else {
            stringResource(id = textoId, *args)
        },
        style = typography.headlineLarge,
        color = colorScheme.onBackground
    )
}