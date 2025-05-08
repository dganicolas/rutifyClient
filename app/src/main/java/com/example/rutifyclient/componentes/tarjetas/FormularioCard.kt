package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.textos.TextoTitulo

@Composable
fun FormularioCard(
    tituloId: Int,
    contenido: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        shape = shapes.medium,
        border = BorderStroke(4.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextoTitulo(textoId = tituloId)
            contenido()
        }
    }
}