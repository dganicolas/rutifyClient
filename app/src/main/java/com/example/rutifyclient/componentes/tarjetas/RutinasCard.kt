package com.example.rutifyclient.componentes.tarjetas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.textos.TextoTitulo

@Composable
fun RutinasCard(
    contenido: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        shape = shapes.medium,
        border = BorderStroke(4.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        LazyColumn (
            modifier = Modifier
                .padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item{contenido()}
        }
    }
}