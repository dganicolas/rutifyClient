package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaBusquedaRutina
import com.example.rutifyclient.viewModel.rutinas.RutinasFavoritasViewModel

@Composable
fun RutinasFavoritas(navControlador: NavHostController) {

    val viewModel: RutinasFavoritasViewModel = viewModel()
    val sinInternet by viewModel.toastMostrado.observeAsState(false)
    val rutinas by viewModel.rutinas.observeAsState(emptyList())
    val estado by viewModel.estado.observeAsState(true)
    val context = LocalContext.current

    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerRutinasFavoritas(context)
        },
        topBar = ({
            TopBarBase(R.string.rutinasFavoritas,({
                Icono(
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    descripcion = R.string.volver,
                    onClick = {navControlador.popBackStack()},
                    tint = colorScheme.onBackground
                )
            }))
        })

    ) {
        LaunchedEffect(Unit) {
            viewModel.obtenerRutinasFavoritas(context)
        }
       Box(
           Modifier.padding(it)
       ){
           PantallaBusquedaRutinaLocal(
               rutinas,
               estado,
               navControlador,
               { }) {
           }
       }
    }
}