package com.example.rutifyclient.pantalla.comunidad.comentarios

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaComentarios
import com.example.rutifyclient.viewModel.foro.comentarios.DetallesComentariosViewModel
import java.time.LocalDate

@Composable
fun DetallesComentario(navControlador: NavHostController, urlComentario: String) {
    val viewModel: DetallesComentariosViewModel = viewModel()
    val comentarios by viewModel.comentarios.observeAsState(emptyList())
    val esAdmin by viewModel.esSuyaOEsAdmin.observeAsState(false)
    val textoComentario by viewModel.textoComentario.observeAsState("")
    val mostrarVentanaEliminarComentario by viewModel.mostrarVentanaEliminarComentario.observeAsState(
        false
    )
    val comentarioPadre by viewModel.comentarioPadre.observeAsState(
        ComentarioDto(
            "", "", "", "",
            LocalDate.now(), "", "", ""
        )
    )
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
    val estadosLista by viewModel.listaEstado.observeAsState(emptyMap())

    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
        viewModel.comprobarAdmin()
        viewModel.obtenerComentarioPadre(urlComentario)
        viewModel.obtenerComentarios()
    }

    PantallaBase(
        viewModel = viewModel(),
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        topBar = ({
            TopBarBase(
                R.string.hilo,
                args = arrayOf(comentarioPadre.nombreUsuario),
                navigationIcon = ({
                    Icono(
                        descripcion = R.string.volver,
                        icono = Icons.AutoMirrored.Filled.ArrowBack,
                        onClick = {
                            navControlador.popBackStack()
                        },
                        tint = colorScheme.onBackground

                    )
                })
            ) {
                Icono(
                    descripcion = R.string.favorito,
                    icono = Icons.Default.Report,
                    onClick = { },
                    tint = colorScheme.onBackground
                )
            }
        })
    ) {
        if (mostrarVentanaEliminarComentario) {
            AlertDialogConfirmar(
                titulo = R.string.eliminarComentario,
                mensaje = R.string.accionIrreversible,
                aceptar = {
                    viewModel.mostrarventanaEliminar()
                    viewModel.borrarComentario { estado -> if(estado) navControlador.popBackStack() }
                },
                denegar = { viewModel.mostrarventanaEliminar() }
            )
        }
        TarjetaNormal(modifierTarjeta = Modifier.padding(it)) {
            Column {
                PantallaComentarios(
                    usuario.idFirebase,
                    esAdmin,
                    { comentario ->
                        viewModel.guardarComentarioAEliminar(comentario)
                        viewModel.mostrarventanaEliminar()
                    },
                    listOf(comentarioPadre),
                    navControlador,
                    estadosLista,
                    false,
                    modifier = Modifier.height(150.dp),
                    maxImagen = 50.dp
                )
                CampoTexto(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 150.dp) // Ajusta el alto como prefieras
                        .verticalScroll(rememberScrollState()),
                    value = textoComentario,
                    onValueChange = { viewModel.setTextoComentario(it) },
                    textoIdLabel = R.string.escribeComentario, // crea en strings.xml
                    singleLine = false,
                    maxLines = 4
                )
                ButtonPrincipal(R.string.publicar, { viewModel.crearComentario() })

                PantallaComentarios(
                    usuario.idFirebase,
                    (esAdmin || comentarioPadre.idFirebase == usuario.idFirebase),
                    { comentario ->
                        viewModel.guardarComentarioAEliminar(comentario)
                        viewModel.mostrarventanaEliminar()
                    },
                    comentarios,
                    navControlador,
                    estadosLista,
                    false
                )
            }
        }
    }
}