package com.example.rutifyclient.pantalla.rutinas.hacerRutina

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.dialogoDeAlerta.PuntuarRutinaDialog
import com.example.rutifyclient.componentes.ventanas.ventanaModal
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.voto.VotodDto
import com.example.rutifyclient.pantalla.commons.Cargando
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.rutinas.EjercicioRutinasViewModel


@Composable
fun HacerEjercicioRutina(navControlador: NavHostController) {
    val viewModel: EjercicioRutinasViewModel = viewModel()
    val tiempo by viewModel.tiempo.observeAsState(0)
    val finalizado by viewModel.finalizado.observeAsState(true)
    val voto by viewModel.voto.observeAsState(VotodDto("", "", "", "",0.0f))
    val ventanaPuntuarRutina by viewModel.VentanaPuntuarRutina.observeAsState(false)
    val estadisticasDtoCalculadas by viewModel.estadisticasDtoCalculadas.observeAsState(
        EstadisticasDto("", 0.0, 0.0, 0.0,0.0, 0.0, 0.0, 0.0, 0,0.0)
    )
    val estadisticas by viewModel.estadisticas.observeAsState(
        EstadisticasDto("", 0.0, 0.0, 0.0,0.0, 0.0, 0.0, 0.0, 0,0.0)
    )
    val ejercicio by viewModel.ejercicio.observeAsState(
        EjercicioDto(
            "",
            "",
            "",
            "",
            "",
            "",
            0.0,
            0.0,
            0
        )
    )
    val cancelado by viewModel.cancelado.observeAsState(false)
    val estado by viewModel.estado.observeAsState(false)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    LaunchedEffect(finalizado) {
        viewModel.detenerTemporizador()
    }

    LaunchedEffect(Unit) {
        viewModel.cargarEjercicio()
        viewModel.obtenervoto()
        viewModel.obtenerEstadisticas()
        viewModel.iniciarTemporizador()
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.cargarEjercicio()
            viewModel.obtenervoto()
            viewModel.obtenerEstadisticas()
            viewModel.iniciarTemporizador()
        }
    ) {
        Box(Modifier.padding(it)){
            ventanaModal {
                val campos = listOf(
                    R.string.descripcionEjercicio to ejercicio.descripcion,
                    R.string.grupoMuscularRutinaEjercicio to ejercicio.grupoMuscular,
                    R.string.equipoRutinaEjercicio to ejercicio.equipo
                )
                if (!finalizado && !cancelado) {
                    pantallaHacerejercicio(
                        campos,
                        ejercicio,
                        tiempo,
                        { viewModel.cancelarRutina() }, { viewModel.siguienteEjercicio() })
                }
                if (finalizado) {
                    pantallaFinal(estado, estadisticas, estadisticasDtoCalculadas, {
                        viewModel.guardarEstadisticaDiaria()
                        viewModel.guardarProgreso() { exito ->
                            if (exito) {
                                navControlador.popBackStack()
                            }
                        }
                    }, { viewModel.VentanaPuntuarRutina(true) })
                }
                if (!estado) {
                    Cargando()
                }
                if (cancelado) {
                    navControlador.popBackStack()
                }
                if (ventanaPuntuarRutina) {
                    PuntuarRutinaDialog(
                        rating = voto.puntuacion,
                        onRatingChanged = { viewModel.cambiarPuntuacion(it) },
                        onDismiss = { viewModel.VentanaPuntuarRutina(false) },
                        onConfirm = { viewModel.guardarVoto() }
                    )
                }

            }
        }
    }
}



