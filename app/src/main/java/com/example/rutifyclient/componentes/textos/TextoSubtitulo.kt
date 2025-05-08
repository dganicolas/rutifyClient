package com.example.rutifyclient.componentes.textos

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun TextoSubtitulo(textoId: Int, vararg args: Any) {
    Text(
        text = stringResource(id = textoId, *args),
        style = typography.titleLarge,
        color = colorScheme.onBackground
    )
}