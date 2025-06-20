package com.example.rutifyclient.pantalla.miZona

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.avatar.cuadroAvatar
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.graficos.PesoGraph
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.ventanas.MostrarCosmeticosLazyRows
import com.example.rutifyclient.componentes.ventanas.ventanaModal
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.pantalla.rutinas.hacerRutina.pantallaHacerejercicio
import com.example.rutifyclient.utils.mostrarVentanaCambiarIconoAvatar
import com.example.rutifyclient.viewModel.usuario.MiZonaViewModel
import java.time.LocalDate
import java.util.Locale

@Composable
fun MiZona(navControlador: NavHostController) {
    val viewModel: MiZonaViewModel = viewModel()
    val usuario by viewModel.usuario.observeAsState(
        UsuarioInformacionDto(
            "",
            "",
            "",
            "",
            false,
            "",
            EstadisticasDto("", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0),
            0,
            LocalDate.now()
        )
    )
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val estado by viewModel.estado.observeAsState(true)
    val metaKcal by viewModel.metaKcal.observeAsState(0)
    val metaRutinas by viewModel.metaRutinas.observeAsState(0)
    val countRutinasFavoritas by viewModel.countRutinasFavoritas.observeAsState(0)
    val metasMinActivos by viewModel.metasMinActivos.observeAsState(0.0f)
    val tiempoRestante by viewModel.tiempoRestante.observeAsState(0)
    val estadisticasDiarias by viewModel.estadisticasDiarias.observeAsState(
        EstadisticasDiariasDto(
            null, "",
            LocalDate.now(), 0.0, 0.0, 0, 0.0
        )
    )
    val ultimosPesos by viewModel.ultimosPesos.observeAsState(listOf(0.0, 0.0, 0.0, 0.0, 0.0))
    val ejercicio by viewModel.ejerciciosReto.observeAsState(
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
    val mostrarVentanacambiarIcono by viewModel.mostrarVentanacambiarIcono.observeAsState(false)
    val mostrarVentanaCambiarRopa by viewModel.mostrarVentanaCambiarRopa.observeAsState(false)
    val imagenes by viewModel.imagenes.observeAsState(emptyList())
    val cosmeticosPorTipo by viewModel.cosmeticosPorTipo.observeAsState(emptyMap())
    val ventanaReto by viewModel.ventanaReto.observeAsState(false)
    val context = LocalContext.current

    val verEstadisticas: () -> Unit = {
        navControlador.navigate(Rutas.Estadisticas)
    }

    LaunchedEffect(Unit) {
        viewModel.obtenerCountRutinasFavoritas(context)
        viewModel.iniciarContadorTiempoRestante()
        viewModel.obtenerUltimos5Pesos()
        viewModel.obtenerUsuario()
        viewModel.obtenerComesticosComprados()
        viewModel.obtenerEstadisticasDiaria()
        viewModel.obtenerObjetivosLocal(context)
    }
    PantallaBase(
        navControlador,
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerUsuario()
        },
        topBar = ({ TopBarBase(R.string.usuario, args = arrayOf(usuario.nombre)) }),
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.MiZona) })
    ) {

        if (ventanaReto) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .zIndex(1f)
            ) {
                ventanaModal {

                    pantallaHacerejercicio(
                        listOf(
                            R.string.descripcionEjercicio to ejercicio.descripcion,
                            R.string.grupoMuscularRutinaEjercicio to ejercicio.grupoMuscular,
                            R.string.equipoRutinaEjercicio to ejercicio.equipo
                        ),
                        ejercicio,
                        tiempoRestante,
                        { viewModel.cerrarVentanaReto() },
                        { viewModel.puntuarReto() },
                        R.string.completarRetoDiario
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(it)
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            cuadroAvatar(
                usuario,
                { viewModel.mostrarVentanacambiarIcono() },
                { viewModel.mostrarVentanaCambiarRopa() })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { verEstadisticas() },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icono(
                            descripcion = R.string.descKcal,
                            icono = Icons.Filled.LocalFireDepartment,
                            modifier = Modifier.size(85.dp),
                            onClick = { verEstadisticas() })
                        TextoInformativo(
                            R.string.metaKcal,
                            estadisticasDiarias.kCaloriasQuemadas,
                            metaKcal
                        )
                        TextoSubtitulo(R.string.kcal)
                    }
                }
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { verEstadisticas() },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icono(
                            descripcion = R.string.descKcal,
                            icono = Icons.Filled.EditNote, modifier = Modifier.size(85.dp),
                            onClick = { verEstadisticas() })
                        TextoInformativo(
                            R.string.metaInt,
                            estadisticasDiarias.ejerciciosRealizados,
                            metaRutinas
                        )
                        TextoSubtitulo(R.string.ejerciciosHechos)
                    }
                }
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { verEstadisticas() },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icono(
                            descripcion = R.string.descKcal,
                            icono = Icons.Filled.AccessTime,
                            onClick = { verEstadisticas() },
                            modifier = Modifier.size(85.dp)
                        )
                        TextoInformativo(
                            R.string.metaDouble,
                            estadisticasDiarias.horasActivo,
                            metasMinActivos
                        )
                        TextoSubtitulo(R.string.horasActivo)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    TarjetaNormal(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        modifierTarjeta = Modifier.clickable { verEstadisticas() }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable { verEstadisticas() },
                        ) {
                            TextoSubtitulo(R.string.pesoUsuario, ultimosPesos[0])
                            PesoGraph(pesos = ultimosPesos.reversed())
                        }
                    }
                }
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { navControlador.navigate("rutinasFavoritas/${""}") },
                    modifierTarjeta = Modifier
                        .fillMaxHeight(0.53f)
                        .clickable { navControlador.navigate("rutinasFavoritas/${""}") }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextoInformativo(R.string.misRutinas, countRutinasFavoritas)
                        TextoInformativo(R.string.rutinasCreadasCount, usuario.countRutinas)
                        TextoInformativo(R.string.ComentariosPublicados, usuario.countComentarios)
                        TextoInformativo(R.string.VotosRealizados, usuario.countVotos)
                    }
                }
            }
            TarjetaNormal(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .clickable {
                        if (usuario.fechaUltimoReto == LocalDate.now()) {
                            viewModel.mostrarToast(R.string.completadoRetoHoy)
                        } else {
                            viewModel.mostrarVentanaRetoDiario()
                        }
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icono(
                        descripcion = R.string.descKcal,
                        icono = Icons.Filled.EmojiEvents,
                        onClick = {
                            if (usuario.fechaUltimoReto == LocalDate.now()) {
                                viewModel.mostrarToast(R.string.completadoRetoHoy)
                            } else {
                                viewModel.mostrarVentanaRetoDiario()
                            }
                        },
                        modifier = Modifier
                            .size(85.dp)
                            .weight(1f)
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextoInformativo(
                            R.string.texto_input,
                            String.format(
                                Locale.US,
                                "%02d:%02d:%02d",
                                tiempoRestante / 3600,
                                (tiempoRestante % 3600) / 60,
                                tiempoRestante % 60
                            )
                        )
                        TextoInformativo(R.string.retoDiario)
                    }
                    Icono(
                        descripcion = R.string.descKcal,
                        icono = Icons.Filled.EmojiEvents,
                        onClick = {
                            if (usuario.fechaUltimoReto == LocalDate.now()) {
                                viewModel.mostrarToast(R.string.completadoRetoHoy)
                            } else {
                                viewModel.mostrarVentanaRetoDiario()
                            }
                        },
                        modifier = Modifier
                            .size(85.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }
    if (mostrarVentanacambiarIcono) {
        mostrarVentanaCambiarIconoAvatar(imagenes) {
            viewModel.cambiarIcono(it)
            viewModel.mostrarVentanacambiarIcono()
        }
    }
    if (mostrarVentanaCambiarRopa) {
        Box{
            RutinasCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize().zIndex(1f)
                    .align(Alignment.TopStart)
            ) {
                item{
                    ButtonPrincipal(R.string.cerrarVentana,onClick = {viewModel.mostrarVentanaCambiarRopa()})
                    MostrarCosmeticosLazyRows(
                        cosmeticosPorTipo,
                        { cosmetico ->
                            viewModel.mostrarVentanaCambiarRopa()
                            viewModel.ponerCosmetico(cosmetico)
                        },
                        R.string.equipar,
                        false
                    )
                    ButtonPrincipal(R.string.cerrarVentana,onClick = {viewModel.mostrarVentanaCambiarRopa()})
                }
            }
        }
    }
}

