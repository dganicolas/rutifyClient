package com.example.rutifyclient.pantalla.login

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConTexto
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogNormal
import com.example.rutifyclient.componentes.tarjetas.FormularioCard
import com.example.rutifyclient.componentes.espaciadores.SpacerHorizontal
import com.example.rutifyclient.componentes.textos.TextoEnlace
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.login.LoginViewModel

@Composable
fun Login(navControlador: NavHostController) {
    val viewModel: LoginViewModel = viewModel()
    val correo by viewModel.textoCorreo.observeAsState("dfdf")
    val contrasena by viewModel.textoContrasena.observeAsState("")
    val tituloVentana by viewModel.tituloVentanaModal.observeAsState(R.string.dato_defecto)
    val mensajeVentana by viewModel.mensajeVentanaModal.observeAsState(R.string.dato_defecto)
    val mostrarVentana by viewModel.mostrarVentanaModal.observeAsState(false)
    val mensajeToast by viewModel.mensajeToast.observeAsState(R.string.dato_defecto)
    val toastMostrado by viewModel.toastMostrado.observeAsState(true)
    val estado by viewModel.estado.observeAsState(true)
    val sinInternet by viewModel.sinInternet.observeAsState(true)
    val mostrarVentanaContrasenaPerdida by viewModel.mostrarVentanaContrasenaPerdida.observeAsState(
        false
    )
    val mostrarContrasena by viewModel.mostrarContrasena.observeAsState(false)
    val context = LocalContext.current
    val activity = context as Activity

    val iniciarSesion: () -> Unit = {
        viewModel.iniciarSesion(correo, contrasena) { exito ->
            if (exito) {
                navControlador.navigate(Rutas.Rutina) {
                    popUpTo(Rutas.Login) { inclusive = true }
                }
            }
        }
    }
    PantallaBase(
        viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = { viewModel.reiniciarInternet()}
    ) {
        if (mostrarVentana) {
            AlertDialogNormal(tituloVentana, mensajeVentana) { viewModel.cerrarVentanaModal() }
        }
        if (mostrarVentanaContrasenaPerdida) {
            AlertDialogConTexto(
                titulo = tituloVentana,
                mensaje = mensajeVentana,
                correo = correo,
                onValueChanged = { viewModel.cambiarCorreo(it) },
                onConfirm = { viewModel.recuperarContrasena() },
                denegar = { viewModel.cerrarVentanaContrasenaPerdida() })
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FormularioCard(R.string.bienvenida) {
                TextoSubtitulo(textoId = R.string.iniciar_sesion)

                CampoTexto(
                    value = correo,
                    onValueChange = { viewModel.cambiarCorreo(it) },
                    textoIdLabel = R.string.correo,
                    error = !viewModel.comprobarCorreo(correo),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                )
                CampoTexto(
                    value = contrasena,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { viewModel.cambiarContrasena(it) },
                    textoIdLabel = R.string.contrasena,
                    error = viewModel.comprobarContrasena(contrasena),
                    visualTransformation = if (mostrarContrasena) PasswordVisualTransformation() else VisualTransformation.None,
                    icono = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    modifierIcon = Modifier
                        .clickable { viewModel.mostrarContrasena(!mostrarContrasena) },
                )
                ButtonPrincipal(
                    textoId = R.string.iniciar_sesion,
                    enabled = estado,
                    onClick = iniciarSesion

                )

                TextoEnlace(
                    textoId = R.string.contrasena_perdida,
                    onClick = { viewModel.ventanaContrasenaPerdida() })
                Row(
                ) {
                    TextoInformativo(textoId = R.string.no_cuenta)
                    SpacerHorizontal(tamano = 4.dp)
                    TextoEnlace(
                        textoId = R.string.registrarse,
                        onClick = {
                            navControlador.popBackStack(Rutas.Login, true)
                            navControlador.navigate(Rutas.Registro)
                        })
                }

                ButtonSecundario(R.string.salir, { activity.finishAffinity() }, enabled = estado)

            }

        }
    }
}