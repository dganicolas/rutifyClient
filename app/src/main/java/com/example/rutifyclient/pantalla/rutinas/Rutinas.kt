package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaBusquedaRutina
import com.example.rutifyclient.viewModel.rutinas.BuscarRutinasViewModel

@Composable
fun Rutinas(navControlador: NavHostController) {
    val viewModel: BuscarRutinasViewModel = viewModel()
    val rutinas by viewModel.listaRutinas.observeAsState(listOf())
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val estado by viewModel.estado.observeAsState(true)
    val obtenerRutinas: () -> Unit = {
        viewModel.obtenerRutinas()
    }
    LaunchedEffect(Unit) {
        obtenerRutinas()
    }

    PantallaBase(
        navControlador,
        viewModel,
        topBar = ({
            TopBarBase(
                R.string.rutinas,
                acciones = {
                    Icono(
                        descripcion = R.string.icono,
                        icono = Icons.Default.Search,
                        onClick = { navControlador.navigate(Rutas.buscarRutinas) })
                })
        }),
        bottomBar = ({
            NavigationBarAbajoPrincipal(
                navControlador,
                Rutas.Rutina
            )
        }),
        cargando = false,
        onReintentar = {
            viewModel.reiniciarPaginacion()
            obtenerRutinas()
        },
        sinInternet = sinInternet,
        iconoFloatingButton = Icons.Default.Add,
        onClickFloatingButton = { navControlador.navigate(Rutas.CrearRutinas) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            PantallaBusquedaRutina(
                rutinas,
                estado,
                navControlador,
                { obtenerRutinas() }) {
                viewModel.reiniciarPaginacion()
                obtenerRutinas()
            }

        }
    }
}

