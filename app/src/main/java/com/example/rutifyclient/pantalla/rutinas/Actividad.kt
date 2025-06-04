package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.deslizables.RatingBar
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaBusquedaRutina
import com.example.rutifyclient.utils.PantallaComentarios
import com.example.rutifyclient.viewModel.rutinas.RutinasFavoritasViewModel

@Composable
fun Actividad(navControlador: NavHostController, idFirebase: String?) {

    val viewModel: RutinasFavoritasViewModel = viewModel()
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val idFirebaseParam by viewModel.idFirebaseParam.observeAsState("")
    val rutinasFavoritas by viewModel.rutinasFavoritas.observeAsState(emptyList())
    val estado by viewModel.estado.observeAsState(true)
    val rutinasCreadas by viewModel.rutinasCreadas.observeAsState(emptyList())
    val comentariosAutor by viewModel.comentariosAutor.observeAsState(emptyList())
    val votosAutor by viewModel.votosAutor.observeAsState(emptyList())
    val listaEstado by viewModel.listaEstado.observeAsState(emptyMap())
    val esAdmin by viewModel.esSuyaOEsAdmin.observeAsState(false)
    val popupEliminarVoto by viewModel.popupEliminarVoto.observeAsState(false)
    val pestanaActiva by viewModel.pestanaActiva.observeAsState(0)
    val mostrarVentanaEliminarComentario by viewModel.mostrarVentanaEliminarComentario.observeAsState(false)
    val context = LocalContext.current
    val pestanas = mutableListOf(
        R.string.rutinasCreadas,
        R.string.comentarioCreados,
    )
    if (idFirebase == "") {
        pestanas.add(R.string.votosCreados)
        pestanas.add(R.string.rutinasFavoritas)
    }
    val cargarRutinas: () -> Unit = {
        when (pestanaActiva) {
            0 -> {
                viewModel.obtenerRutinasCreadas()
            }
            3 -> {
                viewModel.obtenerRutinasFavoritas(context)
            }
            1 -> {
                viewModel.obtenerComentariosAutor()
            }
            2 -> {
                viewModel.obtenerVotosAutor()
            }
        }
    }


    LaunchedEffect(Unit) {

        viewModel.guardarIdFirebase(idFirebase)
        viewModel.comprobarAdmin()
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            cargarRutinas()
        },
        topBar = ({
            if (popupEliminarVoto) {
                AlertDialogConfirmar(
                    titulo = R.string.tituloEliminarRutinaPopUp,
                    mensaje = R.string.mensajeEliminarRutinaPopUp,
                    aceptar = {
                        viewModel.borrarVoto()
                        viewModel.popUpEliminarVoto()
                    },
                    denegar = {
                        viewModel.popUpEliminarVoto()
                    }
                )
            }
            TopBarBase(R.string.actividad, ({
                Icono(
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    descripcion = R.string.volver,
                    onClick = { navControlador.popBackStack() },
                    tint = colorScheme.onBackground
                )
            }))
        })

    ) {
        LaunchedEffect(pestanaActiva) {
            cargarRutinas()
        }
        if (mostrarVentanaEliminarComentario) {
            AlertDialogConfirmar(
                titulo = R.string.eliminarComentario,
                mensaje = R.string.accionIrreversible,
                aceptar = {
                    viewModel.mostrarventanaEliminar()
                    viewModel.borrarComentario()
                },
                denegar = { viewModel.mostrarventanaEliminar() }
            )
        }
        Column(Modifier.padding(it)) {
            ScrollableTabRow(selectedTabIndex = pestanaActiva,
                containerColor = colorScheme.background,
                contentColor = colorScheme.onBackground,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pestanaActiva]),
                        color = colorScheme.primary
                    )
                }) {
                pestanas.forEachIndexed { index, textoId ->
                    Tab(
                        selected = pestanaActiva == index,
                        onClick = { viewModel.setPestanaActiva(index) },
                        selectedContentColor = colorScheme.primary,
                        unselectedContentColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        text = { TextoSubtitulo(textoId = textoId) }
                    )
                }
            }
            when (pestanaActiva) {
                0 -> PantallaBusquedaRutina(
                    rutinasCreadas,
                    estado,
                    navControlador,
                    {},
                    { viewModel.obtenerRutinasCreadas() })

                3 -> PantallaBusquedaRutinaLocal(
                    rutinasFavoritas,
                    estado,
                    navControlador,
                    {},
                    { viewModel.obtenerRutinasFavoritas(context) })

                1 -> PantallaComentarios(
                    idFirebaseParam!!, esAdmin, { comentario ->
                        viewModel.guardarComentarioAEliminar(comentario)
                        viewModel.mostrarventanaEliminar()
                    },
                    comentariosAutor,
                    navControlador,
                    listaEstado
                )
                2 -> Column {
                    votosAutor.forEach { voto ->
                        TarjetaNormal( Modifier.padding(16.dp).fillMaxWidth()) {
                            Row {
                                Column {
                                    TextoTitulo(R.string.texto_input, voto.nombreRutina)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    RatingBar(
                                        rating = voto.puntuacion,
                                        onRatingChanged = { nuevaPuntuacion ->
                                            viewModel.actualizarPuntuacion(voto, nuevaPuntuacion)
                                        }
                                    )
                                }
                                Icono(
                                    descripcion = R.string.eliminar,
                                    icono = Icons.Default.Delete,
                                    onClick = {
                                        viewModel.guardarVotoAEliminar(voto)
                                        viewModel.popUpEliminarVoto()
                                    },
                                    tint = colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}