package com.example.rutifyclient.pantalla.rutinas.hacerRutina

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.rutifyclient.componentes.acordeon.Acordeon
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.ejercicio.EjercicioDto


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun pantallaHacerejercicio(
    campos: List<Pair<Int, String>>,
    ejercicio: EjercicioDto,
    tiempo: Int,
    function: () -> Unit,
    siguienteEjercicios: () -> Unit,
    texto: Int = R.string.siguienteEjercicio
) {
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
                    Icono(onClick = {function()},icono = Icons.Filled.Close, descripcion = R.string.cerrarVentana)
                    Spacer(modifier = Modifier.weight(1f))
                    TextoTitulo(R.string.texto_input,ejercicio.nombreEjercicio,)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            item {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(ejercicio.imagen)
                            .build(),
                        imageLoader = ImageLoader.Builder(LocalContext.current)
                            .components {
                                add(ImageDecoderDecoder.Factory())
                            }
                            .build()
                    ),
                    contentDescription = "GIF como imagen estática",
                    modifier = Modifier
                        .width(240.dp)
                        .height(240.dp)
                )
            }
            item{
                TextoTitulo(R.string.repeticionesEjercicioRutina,ejercicio.cantidad )
                TextoTitulo(R.string.texto_input,"%02d:%02d".format(tiempo / 60, tiempo % 60))
            }
            items(campos) { (titulo, valor) ->
                Acordeon(titulo,valor)
            }
            item{
                ButtonPrincipal(texto,{siguienteEjercicios()})
            }
        }
    }
}