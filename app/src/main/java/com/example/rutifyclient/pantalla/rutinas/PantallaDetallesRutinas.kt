package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.acordeon.Acordeon
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.obtenerIconoRutina
import com.example.rutifyclient.viewModel.rutinas.detallesRutinas.DetallesRutinasViewModel

@Composable
fun PantallaDetallesRutinas(idRutina: String, navControlador: NavController) {

    val viewModel: DetallesRutinasViewModel = viewModel()
    val rutina by viewModel.rutina.observeAsState(
        RutinaDTO(
            "",
            "",
            "",
            "",
            "",
            listOf(),
            "",
            0.0f,
            0,
            false
        )
    )
    val estado by viewModel.estado.observeAsState(true)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val favorito by viewModel.favorito.observeAsState(true)
    val esSuyaOEsAdmin by viewModel.esSuyaOEsAdmin.observeAsState(true)
    val ventanaEliminarRutina by viewModel.ventanaEliminarRutina.observeAsState(false)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.obtenerRutina(idRutina)
        viewModel.inicializarBD(context)
        viewModel.comprobarFavorito(context, idRutina)
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerRutina(idRutina)
            viewModel.inicializarBD(context)
            viewModel.comprobarFavorito(context, idRutina)
        },
        topBar = ({
            TopBarBase(R.string.rutinas, ({
                Icono(
                    descripcion = R.string.volver,
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = {
                        navControlador.popBackStack()
                    },
                    tint = colorScheme.onBackground

                )
            })) {
                Icono(
                    descripcion = R.string.favorito,
                    icono = if (favorito) Icons.Default.Star else Icons.Default.StarBorder,
                    onClick = { viewModel.marcarComoFavorita() },
                    tint = colorScheme.onBackground
                )
                if (esSuyaOEsAdmin) {
                    Icono(
                        descripcion = R.string.eliminar,
                        icono = Icons.Default.Delete,
                        onClick = {
                            viewModel.popUpEliminarRutina(true)
                        },
                        tint = colorScheme.onBackground
                    )
                }
            }
        })
    ) {
        if (ventanaEliminarRutina) {
            AlertDialogConfirmar(
                titulo = R.string.tituloEliminarRutinaPopUp,
                mensaje = R.string.mensajeEliminarRutinaPopUp,
                aceptar = {
                    viewModel.borrarRutina() { borrado ->
                        if (borrado) navControlador.navigate(Rutas.Rutina) {
                            popUpTo(Rutas.Rutina) {
                                inclusive = true
                            }
                        }
                    }
                },
                denegar = {
                    viewModel.popUpEliminarRutina(false)
                }
            )
        }
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding() + 5.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = it.calculateBottomPadding() + 5.dp
            )
        ) {
            RutinasCard {
                item {
                    TextoTitulo(R.string.titulo_rutina_detalle, rutina.nombre)
                }
                item {
                    Icono(
                        modifier = Modifier
                            .size(240.dp)  // Tamaño ajustado
                            .clip(RoundedCornerShape(8.dp)),  // Esquinas redondeadas
                        imagen = obtenerIconoRutina(rutina.imagen),  // Usamos el método que asigna los iconos
                        descripcion = R.string.descripcionIconoRutina,
                        onClick = {}
                    )
                }
                item {
                    Acordeon(R.string.titulo_descripcion, rutina.descripcion)
                }
                item {
                    Acordeon(
                        R.string.titulo_ejercicios,
                        rutina.ejercicios.map { it.nombreEjercicio }.joinToString(", ")
                    )
                }
                item {
                    Acordeon(R.string.titulo_equipo, rutina.equipo)
                }
                item {
                    ButtonPrincipal(R.string.empezar,
                        enabled = estado,
                        onClick = {
                            viewModel.hacerRutina()
                            navControlador.navigate(Rutas.HacerEjercicio)
                        })
                }
            }
        }
    }
}




