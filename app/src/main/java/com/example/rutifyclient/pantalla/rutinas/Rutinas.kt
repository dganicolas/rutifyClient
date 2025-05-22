package com.example.rutifyclient.pantalla.rutinas

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.SinConexionPantalla
import com.example.rutifyclient.utils.PantallaBusquedaRutina
import com.example.rutifyclient.viewModel.rutinas.BuscarRutinasViewModel

@Composable
fun Rutinas(modifier: Modifier, navControlador: NavHostController) {
    val viewModel: BuscarRutinasViewModel = viewModel()
    val rutinas by viewModel.listaRutinas.observeAsState(listOf())
    val mensajeToast by viewModel.mensajeToast.observeAsState(R.string.dato_defecto)
    val toastMostrado by viewModel.toastMostrado.observeAsState(true)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val estado by viewModel.estado.observeAsState(true)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.obtenerRutinas()
    }
    LaunchedEffect(mensajeToast) {
        if (!toastMostrado) {
            Toast.makeText(context, mensajeToast, Toast.LENGTH_LONG).show()
            viewModel.toastMostrado()
        }
    }
    if (sinInternet) {
        SinConexionPantalla {
            viewModel.reiniciarPaginacion()
            viewModel.obtenerRutinas()

        }
    } else {
        Column(modifier = modifier) {
            PantallaBusquedaRutina(
                rutinas,
                estado,
                navControlador,
                { viewModel.obtenerRutinas() }) {
                viewModel.reiniciarPaginacion()
                viewModel.obtenerRutinas()
            }
        }
    }

}

