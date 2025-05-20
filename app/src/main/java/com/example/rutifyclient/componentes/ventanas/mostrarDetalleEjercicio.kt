package com.example.rutifyclient.componentes.ventanas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.ejercicio.EjercicioDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun mostrarDetalleEjercicio(ejercicioSeleccionado: EjercicioDto, volverAtrasVentana: () -> Unit, anadirEjercicioALaLista: () -> Unit) {
    val campos = listOf(
        R.string.descripcionEjercicio to ejercicioSeleccionado.descripcion,
        R.string.grupoMuscular to ejercicioSeleccionado.grupoMuscular,
        R.string.equipo to ejercicioSeleccionado.equipo
    )
    ventanaModal {
        Box {
            RutinasCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.TopStart)
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopStart)
                            .background(colorScheme.surface),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icono(onClick = { volverAtrasVentana() },icono = Icons.AutoMirrored.Filled.ArrowBack, descripcion = R.string.cerrarVentana)
                        Spacer(modifier = Modifier.weight(1f))
                        TextoTitulo(R.string.texto_input,ejercicioSeleccionado.nombreEjercicio,)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                // Agrega cada campo como item en la LazyColumn
                item {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(ejercicioSeleccionado.imagen)
                                .build(),
                            imageLoader = ImageLoader.Builder(LocalContext.current)
                                .components {
                                    add(ImageDecoderDecoder.Factory())
                                }
                                .build()
                        ),
                        contentDescription = "GIF como imagen estática",
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                    )
                }
                items(campos) { (titulo, valor) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextoTitulo(titulo, "")
                        TextoInformativo(R.string.texto_input, valor)
                    }
                }
                item{
                    ButtonPrincipal(R.string.anadirEjercicio,{anadirEjercicioALaLista()})
                }
            }
        }
    }
}