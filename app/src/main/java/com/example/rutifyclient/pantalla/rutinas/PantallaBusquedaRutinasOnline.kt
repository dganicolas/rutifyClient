package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
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
        viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            CampoTexto(
                value = textoBusqueda,
                textoIdLabel = R.string.buscarRutinas,
                onValueChange = {viewModel.onChangeNombreBusqueda(it)}
            )
            Button(
                onClick = {
                    viewModel.buscarRutinas(
                        nombre = textoBusqueda
                    )
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Buscar")
            }
            PantallaBusquedaRutina(
                listaRutinas,
                estado,
                navControlador,
                {  }) {
            }
        }
    }
}