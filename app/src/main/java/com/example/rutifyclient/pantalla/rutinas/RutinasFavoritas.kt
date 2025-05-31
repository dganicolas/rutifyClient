package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
    val sinInternet by viewModel.sinInternet.observeAsState(false)
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
                viewModel.obtenerRutinasCreadas()
            } else {
                viewModel.obtenerRutinasFavoritas(context)

            }
        }

        Column(Modifier.padding(it)) {
            ScrollableTabRow(selectedTabIndex = pestanaActiva,
                containerColor = colorScheme.background,
                contentColor = colorScheme.onBackground,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pestanaActiva]),
                        color = colorScheme.primary
                    )
                }) {

                Tab(
                    selected = pestanaActiva == 0,
                    onClick = { viewModel.setPestanaActiva(0) },
                    selectedContentColor = colorScheme.primary,
                    unselectedContentColor = colorScheme.onSurface.copy(alpha = 0.6f),
                    text = { TextoSubtitulo(textoId = R.string.rutinasCreadas) }
                )
                if (idFirebase == "") {
                    Tab(
                        selected = pestanaActiva == 1,
                        onClick = { viewModel.setPestanaActiva(1) },
                        selectedContentColor = colorScheme.primary,
                        unselectedContentColor = colorScheme.onSurface.copy(alpha = 0.6f),
                        text = { TextoSubtitulo(textoId = R.string.rutinasFavoritas) }
                    )
                }
                Tab(
                    selected = pestanaActiva == 2,
                    onClick = { viewModel.setPestanaActiva(2) },
                    selectedContentColor = colorScheme.primary,
                    unselectedContentColor = colorScheme.onSurface.copy(alpha = 0.6f),
                    text = { TextoSubtitulo(textoId = R.string.comentarioCreados) }
                )
                Tab(
                    selected = pestanaActiva == 3,
                    onClick = { viewModel.setPestanaActiva(3) },
                    selectedContentColor = colorScheme.primary,
                    unselectedContentColor = colorScheme.onSurface.copy(alpha = 0.6f),
                    text = { TextoSubtitulo(textoId = R.string.votosCreados) }
                )
            }
            if (idFirebase == "") {
                when (pestanaActiva) {
                    0 -> PantallaBusquedaRutina(rutinasCreadas, estado, navControlador, {}, {})

                    1 -> PantallaBusquedaRutinaLocal(
                        rutinasFavoritas,
                        estado,
                        navControlador,
                        {},
                        {})
                }
            } else {
                when (pestanaActiva) {
                    0 -> PantallaBusquedaRutina(rutinasCreadas, estado, navControlador, {}, {})
                }
            }

        }
    }
}