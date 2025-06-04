package com.example.rutifyclient.pantalla.comunidad

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.ventanas.dialogoCrearComentario
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaComentarios
import com.example.rutifyclient.viewModel.foro.comentarios.ForoViewModel
import java.time.LocalDate

@Composable
fun Foro(navControlador: NavHostController) {
    val viewModel: ForoViewModel = viewModel()
    val comentarios by viewModel.comentarios.observeAsState(emptyList())
    val mostrarDialogo by viewModel.mostrarDialogoComentario.observeAsState(false)
    val mostrarVentanaEliminarComentario by viewModel.mostrarVentanaEliminarComentario.observeAsState(
        false
    )
    val textoComentario by viewModel.textoComentario.observeAsState("")
    val imagenUri by viewModel.imagenUri.observeAsState()
    val expanded by viewModel.expanded.observeAsState(false)
    val estadoAnimoSeleccionado by viewModel.estadoAnimoSeleccionado.observeAsState("F")
    val esAdmin by viewModel.esSuyaOEsAdmin.observeAsState(false)
    val estado by viewModel.estado.observeAsState(false)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val listaEstados by viewModel.listaEstado.observeAsState(emptyMap())
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

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.comprobarAdmin()
            viewModel.setImagenUri(it)
        }
    }
    val cameraUri by viewModel.cameraUri.observeAsState()

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && cameraUri != null) {
                viewModel.setImagenUri(cameraUri!!)
            }
        }
    LaunchedEffect(cameraUri) {
        cameraUri?.let {
            takePictureLauncher.launch(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
        viewModel.comprobarAdmin()
        viewModel.obtenerComentarios()
    }



    PantallaBase(
        viewModel = viewModel(),
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {viewModel.obtenerUsuario()
            viewModel.comprobarAdmin()
            viewModel.obtenerComentarios()},
        topBar = {
            if (mostrarDialogo) {
                TopBarBase(
                    R.string.crearComentarioNuevo,
                    navigationIcon = {
                        Icono(
                            descripcion = R.string.icono,
                            icono = Icons.Default.Close,
                            onClick = { viewModel.cerrarDialogoComentario() }
                        )
                    }
                )
            } else {
                TopBarBase(
                    R.string.verComentarios,
                    acciones = ({
                        Icono(
                            descripcion = R.string.icono,
                            icono = Icons.Default.Search,
                            onClick = { navControlador.navigate(Rutas.BuscarComentarios) }
                        )
                    })
                )
            }
        },
        bottomBar = ({
            NavigationBarAbajoPrincipal(
                navControlador,
                Rutas.Comunidad
            )
        }),
        iconoFloatingButton = if (!mostrarDialogo) Icons.Default.AddComment else null,
        onClickFloatingButton = { viewModel.abrirDialogoComentario() }
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
            if (mostrarDialogo) {
                dialogoCrearComentario(
                    textoComentario,
                    { viewModel.cerrarDialogoComentario() },
                    { viewModel.setTextoComentario(it) },
                    launcher,
                    imagenUri,
                    { viewModel.crearComentario(context) }, context,
                    { viewModel.crearUriParaFoto(context) },
                    expanded,
                    { viewModel.setExpanded(it) },
                    estadoAnimoSeleccionado,
                    listaEstados,
                    { viewModel.setEstadoAnimo(it) },
                    estado
                )
            } else
                PantallaComentarios(
                    usuario.idFirebase,
                    esAdmin,
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




