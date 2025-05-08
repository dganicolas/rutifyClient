package com.example.rutifyclient.componentes.barras

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarConFavorito(
    titulo: String,
    esFavorito: Boolean,
    onVolverClick: () -> Unit,
    onFavoritoClick: () -> Unit
) {
    TopAppBar(
        title = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = titulo)
            }
        },
        navigationIcon = {
            IconButton(onClick = onVolverClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        },
        actions = {
            IconButton(onClick = onFavoritoClick) {
                Icon(
                    imageVector = if (esFavorito) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Favorito"
                )
            }
        },
        modifier = Modifier
            .height(56.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.onBackground,
            titleContentColor = colorScheme.background,
            navigationIconContentColor = colorScheme.background,
            actionIconContentColor = colorScheme.background
        )
    )
}