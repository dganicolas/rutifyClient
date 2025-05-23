package com.example.rutifyclient.componentes.barras

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.textos.TextoTitulo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarConFavorito(
    titulo: Int,
    esFavorito: Boolean,
    esSuyaOEsAdmin: Boolean,
    onVolverClick: () -> Unit,
    onFavoritoClick: () -> Unit,
    onBorrarClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                TextoTitulo(textoId = titulo)
            }
        },
        navigationIcon = {
            IconButton(onClick = onVolverClick) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver", tint = colorScheme.onBackground
                )
            }
        },
        actions = {
            Icono(
                descripcion = R.string.favorito,
                icono = if (esFavorito) Icons.Default.Star else Icons.Default.StarBorder,
                onClick = onFavoritoClick,
                tint = colorScheme.onBackground
            )
            if (esSuyaOEsAdmin) {
                Icono(
                    descripcion = R.string.eliminar,
                    icono = Icons.Default.Delete,
                    onClick = onBorrarClick,
                    tint = colorScheme.onBackground
                )
            }
        },
        modifier = Modifier
            .height(56.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.background,
            titleContentColor = colorScheme.onSurface,
            navigationIconContentColor = colorScheme.onSurface,
            actionIconContentColor = colorScheme.onSurface
        )
    )
}