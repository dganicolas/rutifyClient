package com.example.rutifyclient.pantalla.comunidad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
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
import com.example.rutifyclient.componentes.avatar.cuadroAvatar
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.graficos.PesoGraph
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.PerfilViewModel
import java.time.LocalDate

@Composable
fun perfil(navControlador: NavHostController, idFirebaseParam: String) {
    val viewModel: PerfilViewModel = viewModel()
    val usuario by viewModel.usuario.observeAsState(
        UsuarioInformacionDto(
            "",
            "",
            "",
            "",
            false,
            "",
            EstadisticasDto("", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0),
            0,
            LocalDate.now()
        )
    )
    val ultimosPesos by viewModel.ultimosPesos.observeAsState(listOf(0.0, 0.0, 0.0, 0.0, 0.0))
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val estado by viewModel.estado.observeAsState(true)
    val noExiste by viewModel.noExiste.observeAsState(false)
    val idFirebase by viewModel.idFirebase.observeAsState("")
    val perfilprivado by viewModel.perfilprivado.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.obtenerIdFirebase(idFirebaseParam)
        viewModel.obtenerUsuario()
        viewModel.obtenerUltimos5Pesos()
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerUsuario()
        },
        topBar = ({
            TopBarBase(R.string.usuario, args = arrayOf(usuario.nombre), navigationIcon = ({
                Icono(
                    descripcion = R.string.volver,
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = {
                        navControlador.popBackStack()
                    },
                    tint = colorScheme.onBackground

                )
            }))
        }),
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (perfilprivado) {
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextoTitulo(R.string.perfilPrivado)
                    }
                }
                return@PantallaBase
            }
            if(noExiste){
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextoTitulo(R.string.noEncontrado)
                    }
                }
                return@PantallaBase
            }
            cuadroAvatar(usuario)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    TarjetaNormal(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            TextoSubtitulo(R.string.pesoUsuario, ultimosPesos[4])
                            PesoGraph(pesos = ultimosPesos)
                        }
                    }
                }
               Column(
                   verticalArrangement = Arrangement.spacedBy(5.dp)
               ) {
                   TarjetaNormal(
                       modifier = Modifier
                           .padding(5.dp)
                           .clickable { navControlador.navigate("rutinasFavoritas/${idFirebase}") },
                       modifierTarjeta = Modifier
                           .fillMaxHeight(0.22f).fillMaxWidth()
                           .clickable { navControlador.navigate("rutinasFavoritas/${idFirebase}") }
                   ) {
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           TextoTitulo(R.string.Rutinas)
                           TextoSubtitulo(R.string.rutinasCreadasCount, usuario.countRutinas)
                       }
                   }
                   TarjetaNormal(
                       modifier = Modifier
                           .padding(5.dp).fillMaxHeight(0.22f).fillMaxWidth()
                           .clickable { navControlador.navigate("rutinasFavoritas/${idFirebase}") },
                       modifierTarjeta = Modifier
                           .clickable { navControlador.navigate("rutinasFavoritas/${idFirebase}") }
                   ) {
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           TextoTitulo(R.string.Comentarios)
                           TextoSubtitulo(R.string.ComentariosPublicados, usuario.countRutinas)
                       }
                   }
               }
            }
        }
    }
}