package com.example.rutifyclient.componentes

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextoTitulo(@StringRes textoId: Int) {
    Text(
        text = stringResource(id = textoId),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun TextoSubtitulo(@StringRes textoId: Int) {
    Text(
        text = stringResource(id = textoId),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun TextoInformativo(@StringRes textoId: Int) {
    Text(
        text = stringResource(id = textoId),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun TextoBotones(@StringRes textoId: Int) {
    Text(
        text = stringResource(id = textoId),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun TextoEnlace(@StringRes textoId: Int, onClick: () -> Unit) {
    Text(
        text = stringResource(id = textoId),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable { onClick() }
    )
}
