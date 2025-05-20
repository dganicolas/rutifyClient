package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaNormal (
    modifier: Modifier = Modifier
        .padding(16.dp).fillMaxSize(),
    contenido: @Composable () -> Unit,

    ) {
    OutlinedCard(
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