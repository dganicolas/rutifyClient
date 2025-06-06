package com.example.rutifyclient.pantalla.comunidad.buscador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaBusquedaUsuarios
import com.example.rutifyclient.utils.PantallaComentarios
import com.example.rutifyclient.viewModel.BuscadorViewModel

@Composable
fun Buscador(navControlador: NavHostController) {
    val viewModel: BuscadorViewModel = viewModel()
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val textoBusqueda by viewModel.textoBusqueda.observeAsState("")
    val estado by viewModel.estado.observeAsState(true)
    val pestanaActiva by viewModel.pestanaActiva.observeAsState(0)
    val listaUsuarios by viewModel.listaUsuarios.observeAsState(emptyList())
    val idFirebase by viewModel.idFirebase.observeAsState("")
    val esSuyaOEsAdmin by viewModel.esSuyaOEsAdmin.observeAsState(false)
    val comentarios by viewModel.listaComentarios.observeAsState(emptyList())
    val listaEstados by viewModel.listaEstado.observeAsState(emptyMap())
    val mostrarVentanaEliminarComentario by viewModel.mostrarVentanaEliminarComentario.observeAsState(false)
    val pestanas = listOf(
        R.string.buscarCuentas,
        R.string.buscarComentariosPorAutor,
    )
    val buscar: () -> Unit = {
        if(pestanaActiva == 0 && textoBusqueda != ""){
            viewModel.buscarCuentasPorNombre()
        }
        if(pestanaActiva == 1 && textoBusqueda != ""){
            viewModel.buscarComentariosPorAutor()
        }
    }
    LaunchedEffect(pestanaActiva) {
        buscar()
    }
    LaunchedEffect(Unit) {
        viewModel.comprobarAdmin()
    }
    PantallaBase(
        navControlador,
        viewModel = viewModel(),
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {},
        topBar = ({
            TopBarBase(R.string.buscar, ({
                Icono(
                    descripcion = R.string.volver,
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = {
                        navControlador.popBackStack()
                    },
                    tint = colorScheme.onBackground

                )
            }))
        })
    ) {
        if (mostrarVentanaEliminarComentario) {
            AlertDialogConfirmar(
                titulo = R.string.eliminarComentario,
                mensaje = R.string.accionIrreversible,
                aceptar = {
                    viewModel.borrarComentario()
                    viewModel.mostrarventanaEliminar()
                },
                denegar = { viewModel.mostrarventanaEliminar() }
            )
        }
        Column(modifier = Modifier.padding(it)) {
            TarjetaNormal(Modifier.padding(16.dp)) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CampoTexto(
                            Modifier.weight(1f),
                            value = textoBusqueda,
                            textoIdLabel = R.string.buscar,
                            onValueChange = { viewModel.onChangeNombreBusqueda(it) }
                        )
                        Icono(
                            descripcion = R.string.icono,
                            icono = Icons.Default.Search,
                            onClick = { buscar() },
                            modifier = Modifier
                                .size(45.dp)
                                .weight(0.12f)
                        )
                    }
                    ScrollableTabRow(selectedTabIndex = pestanaActiva,
                        containerColor = colorScheme.surface,
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
                                text = { TextoInformativo(textoId = textoId) }
                            )
                        }
                    }
                }
            }
            if(pestanaActiva ==0){
                PantallaBusquedaUsuarios(listaUsuarios, navControlador)
            }else if(pestanaActiva ==1){
                PantallaComentarios(
                    idFirebase!!,
                    esSuyaOEsAdmin,
                    { comentario ->
                        viewModel.guardarComentarioAEliminar(comentario)
                        viewModel.mostrarventanaEliminar()
                    },
                    comentarios,
                    navControlador,
                    listaEstados
                )
            }
        }
    }
}

