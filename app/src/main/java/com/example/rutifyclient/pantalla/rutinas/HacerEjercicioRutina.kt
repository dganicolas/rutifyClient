package com.example.rutifyclient.pantalla.rutinas

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
import com.example.rutifyclient.componentes.ventanas.ventanaModal
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.viewModel.rutinas.EjercicioRutinasViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HacerEjercicioRutina(navControlador: NavHostController) {
    val viewModel: EjercicioRutinasViewModel = viewModel()
    val tiempo by viewModel.tiempo.observeAsState(0)
    LaunchedEffect(Unit) {
        viewModel.cargarEjercicio()
        viewModel.obtenerEstadisticas()
        delay(3000)
        viewModel.iniciarTemporizador()
    }
    val ejercicio by viewModel.ejercicio.observeAsState(EjercicioDto("","","","","","",0.0,0.0,0))
    ventanaModal{
        val campos = listOf(
            R.string.descripcionEjercicio to ejercicio.descripcion,
            R.string.grupoMuscularRutinaEjercicio to ejercicio.grupoMuscular,
            R.string.equipoRutinaEjercicio to ejercicio.equipo
        )
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
                            Icono(onClick = { viewModel.finalizarRutina() },icono = Icons.Filled.Close, descripcion = R.string.cerrarVentana)
                            Spacer(modifier = Modifier.weight(1f))
                            TextoTitulo(R.string.texto_input,ejercicio.nombreEjercicio,)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    // Agrega cada campo como item en la LazyColumn
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
                        ButtonPrincipal(R.string.siguienteEjercicio,{viewModel.siguienteEjercicio()})
                    }
                }
            }
        }

}
