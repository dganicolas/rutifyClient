package com.example.rutifyclient.pantalla.rutinas

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.rutifyclient.componentes.Cargando
import com.example.rutifyclient.componentes.SinConexionPantalla
import com.example.rutifyclient.componentes.acordeon.Acordeon
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.barScaffolding.PantallaConBarraSuperiorRutinas
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
    val mensajeToast by viewModel.mensajeToast.observeAsState(R.string.dato_defecto)
    val toastMostrado by viewModel.toastMostrado.observeAsState(true)
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
    LaunchedEffect(mensajeToast) {
        if (!toastMostrado) {
            Toast.makeText(context, mensajeToast, Toast.LENGTH_LONG).show()
            viewModel.toastMostrado()
        }
    }
    if(ventanaEliminarRutina){
        AlertDialogConfirmar(
            titulo = R.string.tituloEliminarRutinaPopUp,
            mensaje = R.string.mensajeEliminarRutinaPopUp,
            aceptar = {viewModel.borrarRutina() {
                if (it) navControlador.navigate(Rutas.Rutina) {
                    popUpTo(Rutas.Rutina) {
                        inclusive = true
                    }
                }
            }},
            denegar = {
                viewModel.popUpEliminarRutina(false)
            }
        )
    }
    PantallaConBarraSuperiorRutinas(
        {
            navControlador.navigate(Rutas.Rutina) {
                popUpTo(Rutas.Rutina) { inclusive = true }
            }
        },
        { viewModel.marcarComoFavorita() },
        {
            viewModel.popUpEliminarRutina(true)
        },
        favorito,
        esSuyaOEsAdmin = esSuyaOEsAdmin
    ) { innerPading ->
        Box(
            modifier = Modifier.padding(
                top = innerPading.calculateTopPadding() + 5.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = innerPading.calculateBottomPadding() + 5.dp
            )
        ) {
            if (sinInternet) {
                SinConexionPantalla {
                    viewModel.obtenerRutina(idRutina)
                    viewModel.inicializarBD(context)
                    viewModel.comprobarFavorito(context, idRutina)
                }
            } else if (!estado) {
                Cargando()
            } else {
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
}

