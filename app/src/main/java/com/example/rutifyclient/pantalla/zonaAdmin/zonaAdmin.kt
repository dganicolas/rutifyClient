package com.example.rutifyclient.pantalla.zonaAdmin
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.TarjetaComentario
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.ZonaAdminViewModel

@Composable
fun zonaAdmin(navControlador: NavHostController) {
    val viewModel: ZonaAdminViewModel = viewModel()
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val comentarios by viewModel.comentarios.observeAsState(emptyList())
    val estado by viewModel.estado.observeAsState(true)
    val idFirebase by viewModel.idFirebase.observeAsState("")
    val listaEstados by viewModel.listaEstado.observeAsState(emptyMap())

    LaunchedEffect(Unit) {
        viewModel.cargarComentariosNoAprobados()
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerUsuario()
        },
        topBar = ({ TopBarBase(R.string.administrarComentarios)}),
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.MiZona) })
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(comentarios, key = { comentario -> comentario._id!! }) { comentario ->
                TarjetaComentario(idFirebase!!,true,comentario, navControlador,listaEstados,true, 100.dp,
                    { viewModel.eliminarComentario(it) },({viewModel.aprobarComentario(it)}))
                SpacerVertical(10.dp)
            }
        }
}
}