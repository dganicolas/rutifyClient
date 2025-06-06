package com.example.rutifyclient.utils

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.ventanas.ventanaModal
import com.example.rutifyclient.utils.obtenerIconoRutina

@Composable
fun mostrarVentanaCambiarIconoAvatar(imagenes: List<String>, cambio: (String) -> Unit) {
    ventanaModal {
        Box {
            RutinasCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize().zIndex(1f)
                    .align(Alignment.TopStart)
            ) {
                items(imagenes.chunked(2)) { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        fila.forEach { icono ->
                            Icono(
                                imagen = painterResource(id = obtenerAvatarResource(icono)),
                                descripcion = R.string.descripcionIconoRutina,
                                onClick = { cambio(icono) },
                                modifier = Modifier.size(105.dp),
                                estado = false
                            )
                        }
                    }
                }
            }
        }
    }
}