package com.example.rutifyclient.pantalla.barScaffolding

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.rutifyclient.componentes.barras.TopBarConFavorito

@Composable
fun PantallaConBarraSuperiorRutinas(onVolverClick: () -> Unit,
                                    onFavoritoClick: () -> Unit,
                                    onBorrarClick: () -> Unit,
                                    esFavorito: Boolean,
                                    esSuyaOEsAdmin: Boolean,
                                    contenido: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopBarConFavorito(esFavorito =esFavorito,onVolverClick = onVolverClick,onFavoritoClick = onFavoritoClick, onBorrarClick = onBorrarClick, esSuyaOEsAdmin = esSuyaOEsAdmin)
        }
    ) { innerPadding ->
        contenido(innerPadding)
    }

}