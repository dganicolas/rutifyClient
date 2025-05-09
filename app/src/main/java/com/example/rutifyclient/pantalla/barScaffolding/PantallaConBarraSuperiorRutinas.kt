package com.example.rutifyclient.pantalla.barScaffolding

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.rutifyclient.componentes.barras.TopBarConFavorito

@Composable
fun PantallaConBarraSuperiorRutinas(onVolverClick: () -> Unit,
                                    onFavoritoClick: () -> Unit,
                                    contenido: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopBarConFavorito(esFavorito =false,onVolverClick = onVolverClick,onFavoritoClick = onFavoritoClick)
        }
    ) { innerPadding ->
        contenido(innerPadding)
    }

}