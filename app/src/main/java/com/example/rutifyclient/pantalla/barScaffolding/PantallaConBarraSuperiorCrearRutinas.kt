package com.example.rutifyclient.pantalla.barScaffolding

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.rutifyclient.componentes.barras.TopBarConFavorito
import com.example.rutifyclient.componentes.barras.TopBarCrearRutinas

@Composable
fun PantallaConBarraSuperiorCrearRutinas(
    onVolverClick: () -> Unit,
    titulo : Int,
    contenido: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopBarCrearRutinas(titulo = titulo) { onVolverClick() }
        }
    ) { innerPadding ->
        contenido(innerPadding)
    }

}