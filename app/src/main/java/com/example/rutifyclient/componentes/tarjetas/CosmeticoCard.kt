package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.domain.tienda.cosmeticos.Cosmetico


@Composable
fun CosmeticoCard(
    cosmetico: Cosmetico,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TarjetaNormal(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Imagen del cosmético
            AsyncImage(
                model = cosmetico.imagenUrl,
                contentDescription = cosmetico.nombre,
                modifier = Modifier
                    .height(80.dp)
            )
            TextoSubtitulo(R.string.texto_input, cosmetico.nombre)
            TextoSubtitulo(R.string.texto_input, "${cosmetico.precioMonedas} monedas")
            ButtonPrincipal(
                R.string.comprar,
                {
                    onClick()
                },
                modifier = Modifier
            )
        }
    }
}