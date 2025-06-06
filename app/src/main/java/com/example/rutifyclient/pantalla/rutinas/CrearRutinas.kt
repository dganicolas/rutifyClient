package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.miscelaneo.ItemCantidad
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.componentes.ventanas.mostrarDetalleEjercicio
import com.example.rutifyclient.componentes.ventanas.mostrarEjercicios
import com.example.rutifyclient.componentes.ventanas.mostrarVentanaCambiarIconoRutinas
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.obtenerIconoRutina
import com.example.rutifyclient.viewModel.rutinas.CrearRutinasViewModel

@Composable
fun CrearRutinas(navControlador: NavHostController) {
    val viewModel: CrearRutinasViewModel = viewModel()
    val iconoEscogido by viewModel.iconoEscogido.observeAsState("r1")
    val nombre by viewModel.nombre.observeAsState("")
    val limiteNombre by viewModel.limiteNombre.observeAsState(0)
    val limiteDescripcion by viewModel.limiteDescripcion.observeAsState(0)
    val descripcion by viewModel.descripcion.observeAsState("")
    val ejerciciosPorGrupo by viewModel.ejerciciosAgrupados.observeAsState(mapOf())
    val cargando by viewModel.cargando.observeAsState(false)
    val mostrarVentanaAnadirEjercicio by viewModel.mostrarVentanaAnadirEjercicio.observeAsState(
        false
    )
    val mostrarVentanaDetallesEjercicio by viewModel.mostrarVentanaDetallesEjercicio.observeAsState(
        false
    )
    val mostrarVentanacambiarIcono by viewModel.mostrarVentanacambiarIcono.observeAsState(false)
    val imagenes by viewModel.iconosRutina.observeAsState(listOf())
    val ejercicioSeleccionado by viewModel.ejercicioSeleccionado.observeAsState(
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
    val ejerciciosSeleccionadosParaNuevaRutina by viewModel.ejerciciosSeleccionadosParaNuevaRutina.observeAsState(
        listOf()
    )
    val puntosGanados by viewModel.puntosGanados.observeAsState(0)
    val calorias by viewModel.calorias.observeAsState(0)
    val listaEquipo by viewModel.listaEquipo.observeAsState(listOf())
    val mostrarVentanaEliminarEjercicio by viewModel.mostrarVentanaEliminarEjercicio.observeAsState(
        false
    )
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    LaunchedEffect(Unit) {
        viewModel.obtenerEjerciciosDesdeApi()
    }

    if (mostrarVentanaEliminarEjercicio) {
        AlertDialogConfirmar(
            R.string.confirmar,
            R.string.eliminarEjercicio,
            { viewModel.eliminarEjercicio() },
            { viewModel.cerrarVentanaEliminarEjercicio() })
    }
    Box() {

        PantallaBase(
            navControlador,
            viewModel = viewModel,
            cargando = cargando,
            sinInternet = sinInternet,
            onReintentar = { viewModel.obtenerEjerciciosDesdeApi() },
            topBar = ({
                TopBarBase(R.string.crearRutina,({
                    Icono(
                        icono = Icons.AutoMirrored.Filled.ArrowBack,
                        descripcion = R.string.volver,
                        onClick = {navControlador.popBackStack()},
                        tint = colorScheme.onBackground
                    )
                }))
            })
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()
            ) {
                RutinasCard(
                ) {
                    item {
                        TextoTitulo(R.string.icono)
                        Icono(
                            imagen = obtenerIconoRutina(iconoEscogido),
                            descripcion = R.string.descripcionIconoRutina,
                            onClick = { if (!cargando) viewModel.abrirVentanaImagenes() },
                            modifier = Modifier
                                .size(75.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                    item {
                        CampoTexto(
                            value = nombre,
                            onValueChange = { nombre -> viewModel.cambiarNombre(nombre) },
                            textoIdLabel = R.string.nombreRutina,
                            mostrarContador = true,
                            maxLength = limiteNombre,
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                        )
                    }
                    item {
                        CampoTexto(
                            modifier = Modifier
                                .fillMaxWidth().heightIn(min = 120.dp, max = 150.dp)
                                .verticalScroll(rememberScrollState()),
                            value = descripcion,
                            onValueChange = { desc -> viewModel.cambiarDescripcion(desc) },
                            textoIdLabel = R.string.descripcionRutina,
                            singleLine = false,
                            maxLines = 4,
                            mostrarContador = true,
                            maxLength = limiteDescripcion
                        )
                    }
                    item {
                        RutinasCard(
                            modifier = Modifier.height(160.dp),
                        ) {
                            itemsIndexed(ejerciciosSeleccionadosParaNuevaRutina) { index, ejercicio ->
                                ItemCantidad(
                                    nombre = ejercicio.nombreEjercicio,
                                    imagen = ejercicio.imagen,
                                    cantidad = ejercicio.cantidad,
                                    onClick = { viewModel.mostrarVentanaEliminarEjercicio(index) },
                                    onCantidadChange = { viewModel.cambiarCantidad(index, it) }
                                )
                            }
                        }
                    }
                    item { TextoSubtitulo(R.string.equipo, "") }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(listaEquipo) { index ->
                                TarjetaNormal {
                                    TextoInformativo(R.string.texto_input, index)
                                }

                            }
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            TarjetaNormal(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                TextoInformativo(R.string.caloriasEstimadas, calorias)
                            }
                            TarjetaNormal {
                                TextoInformativo(R.string.puntosGanados, puntosGanados)
                            }
                        }
                    }

                    item {
                        ButtonSecundario(
                            R.string.anadirEjercicio,
                            { viewModel.ensenarVentanaAnadirEjercicio() }, enabled = !cargando
                        )
                    }
                    item {
                        ButtonPrincipal(R.string.crearRutina, {
                            viewModel.crearRutina { estado ->
                                if (estado) {
                                    navControlador.navigate(Rutas.Rutina) {
                                        popUpTo(Rutas.Rutina) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }, enabled = !cargando)
                    }

                }
                if (mostrarVentanaAnadirEjercicio) {
                    mostrarEjercicios(ejerciciosPorGrupo, {
                        viewModel.mostrarDetallesEjercicio(
                            it
                        )
                    }, { viewModel.cerrarVentanaEjercicio() })
                }
                if (mostrarVentanaDetallesEjercicio) {
                    mostrarDetalleEjercicio(
                        ejercicioSeleccionado,
                        { viewModel.ensenarVentanaAnadirEjercicio() },
                        { viewModel.anadirEjercicioALaLista() })
                }
                if (mostrarVentanacambiarIcono) {
                    mostrarVentanaCambiarIconoRutinas(imagenes) { viewModel.cambiarIcono(it) }
                }
            }
        }
    }
}

