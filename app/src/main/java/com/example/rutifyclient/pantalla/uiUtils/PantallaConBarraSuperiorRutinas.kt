package com.example.rutifyclient.pantalla.uiUtils

import androidx.compose.runtime.Composable
import com.example.rutifyclient.componentes.barras.TopBarConFavorito

@Composable
fun PantallaConBarraSuperiorRutinas(onVolverClick: () -> Unit,
                                    onFavoritoClick: () -> Unit) {
    TopBarConFavorito("Rutinas",false,onVolverClick,onFavoritoClick)
}