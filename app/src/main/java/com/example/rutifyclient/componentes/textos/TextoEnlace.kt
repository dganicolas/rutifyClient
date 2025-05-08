package com.example.rutifyclient.componentes.textos

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TextoEnlace(textoId: Int, onClick: () -> Unit) {
    Text(
        text = stringResource(id = textoId),
        style = typography.bodyMedium,
        color = colorScheme.primary,
        modifier = Modifier.clickable { onClick() }
    )
}