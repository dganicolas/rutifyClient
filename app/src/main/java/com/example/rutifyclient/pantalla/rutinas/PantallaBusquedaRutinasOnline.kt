package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
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
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaBusquedaRutina
import com.example.rutifyclient.viewModel.rutinas.BusquedaRutinasOnlineViewModel

@Composable
fun PantallaBusquedaRutinasOnline(navControlador: NavHostController) {

    val viewModel: BusquedaRutinasOnlineViewModel = viewModel()
    val textoBusqueda by viewModel.textoBusqueda.observeAsState("")
    val estado by viewModel.estado.observeAsState(false)
    val listaRutinas by viewModel.listaRutinas.observeAsState(emptyList())
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    PantallaBase(
        navControlador,
        viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {},
        topBar = ({
            TopBarBase(R.string.buscarRutinas, ({
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
        Column(modifier = Modifier.padding(it)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                TarjetaNormal(Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CampoTexto(
                            Modifier.weight(1f),
                            value = textoBusqueda,
                            textoIdLabel = R.string.buscarRutinas,
                            onValueChange = { viewModel.onChangeNombreBusqueda(it) }
                        )
                        Icono(
                            descripcion = R.string.icono,
                            icono = Icons.Default.Search,
                            onClick = {viewModel.buscarRutinas(textoBusqueda)},
                            modifier = Modifier.size(45.dp).weight(0.12f))
                    }
                }
                PantallaBusquedaRutina(
                    listaRutinas,
                    estado,
                    navControlador,
                    { }) {
                }
            }
        }

    }
}