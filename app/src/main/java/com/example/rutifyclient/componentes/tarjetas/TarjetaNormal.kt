package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaNormal (
    modifier: Modifier = Modifier
        .padding(16.dp).fillMaxSize(),
    modifierTarjeta: Modifier = Modifier,
    contenido: @Composable () -> Unit,

    ) {
    OutlinedCard(
    modifier = modifierTarjeta,
    shape = shapes.medium,
    border = BorderStroke(4.dp, colorScheme.primary),
    colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.TopCenter
        ){
            contenido()
        }
    }
}