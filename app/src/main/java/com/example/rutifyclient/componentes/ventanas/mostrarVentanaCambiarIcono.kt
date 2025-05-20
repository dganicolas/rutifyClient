package com.example.rutifyclient.componentes.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.utils.obtenerIconoRutina

@Composable
fun mostrarVentanaCambiarIcono(imagenes: List<String>, cambio: (String) -> Unit) {
    ventanaModal {
        Box {
            RutinasCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.TopStart)
            ) {
                items(imagenes.chunked(2)) { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        fila.forEach { icono ->
                            Icono(
                                imagen = obtenerIconoRutina(icono),
                                descripcion = R.string.descripcionIconoRutina,
                                onClick = { cambio(icono) },
                                modifier = Modifier.size(105.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}