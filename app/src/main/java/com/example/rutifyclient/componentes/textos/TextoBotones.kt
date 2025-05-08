package com.example.rutifyclient.componentes.textos

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun TextoBotones(textoId: Int) {
    Text(
        text = stringResource(id = textoId),
        style = typography.bodyLarge,
        color = colorScheme.surface
    )
}