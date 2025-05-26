package com.example.rutifyclient.pantalla.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo

@Composable
fun SinConexionPantalla(onReintentar: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TarjetaNormal(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CloudOff,
                    contentDescription = "Sin conexión",
                    modifier = Modifier.size(96.dp),
                    tint = colorScheme.onBackground
                )
                TextoSubtitulo(R.string.sininternet)
                ButtonSecundario(R.string.reintentar, onClick = {onReintentar()})
            }
        }
    }
}