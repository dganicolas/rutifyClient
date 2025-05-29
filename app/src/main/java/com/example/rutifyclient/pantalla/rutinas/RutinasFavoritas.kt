package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaBusquedaRutina
import com.example.rutifyclient.viewModel.rutinas.RutinasFavoritasViewModel

@Composable
fun RutinasFavoritas(navControlador: NavHostController, idFirebase: String?) {

    val viewModel: RutinasFavoritasViewModel = viewModel()
    val sinInternet by viewModel.toastMostrado.observeAsState(false)
    val rutinasFavoritas by viewModel.rutinasFavoritas.observeAsState(emptyList())
    val estado by viewModel.estado.observeAsState(true)
    val rutinasCreadas by viewModel.rutinasCreadas.observeAsState(emptyList())
    val pestanaActiva by viewModel.pestanaActiva.observeAsState(0)
    val context = LocalContext.current

    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.guardarIdFirebase(idFirebase)
            if (idFirebase == "") {
                viewModel.obtenerRutinasFavoritas(context)
            }
            viewModel.obtenerRutinasCreadas()
        },
        topBar = ({
            TopBarBase(R.string.rutinasFavoritas, ({
                Icono(
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    descripcion = R.string.volver,
                    onClick = { navControlador.popBackStack() },
                    tint = colorScheme.onBackground
                )
            }))
        })

    ) {
        LaunchedEffect(pestanaActiva) {
            if (idFirebase == "" && pestanaActiva == 0) {
                viewModel.obtenerRutinasFavoritas(context)
            } else {
                viewModel.obtenerRutinasCreadas()
            }
        }

        Column(Modifier.padding(it)) {
            if (idFirebase == "") {
                TabRow(selectedTabIndex = pestanaActiva) {
                    Tab(
                        selected = pestanaActiva == 0,
                        onClick = { viewModel.setPestanaActiva(0) },
                        text = { TextoSubtitulo(textoId = R.string.rutinasFavoritas) }
                    )
                    Tab(
                        selected = pestanaActiva == 1,
                        onClick = { viewModel.setPestanaActiva(1) },
                        text = { TextoSubtitulo(textoId = R.string.rutinasCreadas) }
                    )
                }

                when (pestanaActiva) {
                    0 -> PantallaBusquedaRutinaLocal(
                        rutinasFavoritas,
                        estado,
                        navControlador,
                        {},
                        {})

                    1 -> PantallaBusquedaRutina(rutinasCreadas, estado, navControlador, {}, {})
                }
            } else {
                // Solo mostrar rutinas creadas públicas del otro usuario
                PantallaBusquedaRutinaLocal(rutinasFavoritas, estado, navControlador, {}, {})
            }
        }
    }
}