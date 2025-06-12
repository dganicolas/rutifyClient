package com.example.rutifyclient.pantalla.register

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.casillasDeVerificacion.CheckboxConTextoCondiciones
import com.example.rutifyclient.componentes.tarjetas.FormularioCard
import com.example.rutifyclient.componentes.espaciadores.SpacerHorizontal
import com.example.rutifyclient.componentes.selectores.SelectorFechaNacimiento
import com.example.rutifyclient.componentes.selectores.SelectorSexo
import com.example.rutifyclient.componentes.textos.TextoEnlace
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.register.RegistroViewModel

@Composable
fun Registro(viewModel: RegistroViewModel = viewModel(),navControlador: NavHostController) {
    val nombre by viewModel.textoNombre.observeAsState("")
    val estado by viewModel.estado.observeAsState(true)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val fechaNacimiento by viewModel.textoFechaNacimiento.observeAsState("")
    val correo by viewModel.correo.observeAsState("")
    val opcionesSexo by viewModel.opcionesSexo.observeAsState(listOf())
    val opcionEscogida by viewModel.opcionEscogida.observeAsState(0)
    val checboxTerminos by viewModel.checboxTerminos.observeAsState(false)
    val contrasena by viewModel.contrasena.observeAsState("")
    val mostrarContrasena by viewModel.mostrarContrasena.observeAsState(false)
    val mostrarContrasenaConfirmacion by viewModel.mostrarContrasenaConfirmacion.observeAsState(false)
    val contrasenaConfirmacion by viewModel.contrasenaConfirmacion.observeAsState("")
    val context = LocalContext.current
    val activity = context as Activity
    val focusManager = LocalFocusManager.current
    PantallaBase(
        navControlador,
        comprobar = false,
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = { viewModel.reiniciarInternet() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FormularioCard(R.string.nueva_cuenta) {
                TextoInformativo(textoId = R.string.registro_info)
                CampoTexto(
                    modifier = Modifier.fillMaxWidth().testTag("campo_nombre"),
                    value = nombre,
                    onValueChange = { viewModel.cambiarNombre(it) },
                    textoIdLabel = R.string.nombre,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                CampoTexto(
                    modifier = Modifier.fillMaxWidth().testTag("campo_correo"),
                    value = correo,
                    onValueChange = { viewModel.cambiarCorreo(it) },
                    textoIdLabel = R.string.correo,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email,imeAction = ImeAction.Next),
                )

                CampoTexto(
                    modifier = Modifier.fillMaxWidth().testTag("campo_contrasena"),
                    value = contrasena,
                    onValueChange = { viewModel.cambiarContrasena(it) },
                    textoIdLabel = R.string.contrasena,
                    visualTransformation = if (mostrarContrasena) PasswordVisualTransformation() else VisualTransformation.None,
                    icono = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    modifierIcon = Modifier
                        .clickable { viewModel.mostrarContrasena(!mostrarContrasena) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
                )

                CampoTexto(
                    modifier = Modifier.fillMaxWidth().testTag("campo_contrasena_confirmacion"),
                    value = contrasenaConfirmacion,
                    onValueChange = { viewModel.cambiarContrasenaConfirmacion(it) },
                    textoIdLabel = R.string.contrasena_confirmacion,
                    visualTransformation = if (mostrarContrasenaConfirmacion) PasswordVisualTransformation() else VisualTransformation.None,
                    icono = if (mostrarContrasenaConfirmacion) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    modifierIcon = Modifier
                        .clickable { viewModel.mostrarContrasenaConfirmacion(!mostrarContrasenaConfirmacion) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
                )

                SelectorFechaNacimiento(
                    fechaNacimiento = fechaNacimiento,
                    onFechaSeleccionada = { viewModel.cambiarFechaNacimiento(it) },
                    onFechaInvalida = {
                        viewModel.mostrarToast(R.string.fecha_invalida)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions( onDone = {
                        focusManager.clearFocus()
                    })
                )
                SelectorSexo(
                    opciones = opcionesSexo,
                    opcionSeleccionada = opcionEscogida,
                    onSeleccionar = { viewModel.cambiarSexo(it) },
                    textoId = R.string.sexo
                )

                CheckboxConTextoCondiciones(
                    checboxTerminos,
                    { viewModel.cambiarTerminos(it) },
                    R.string.terminos,
                    { viewModel.mostrarPaginaTerminos(context) }
                )

                ButtonPrincipal(textoId = R.string.registrarse, {

                    viewModel.registrarUsuario() {
                        if (it) {
                            navControlador.popBackStack(Rutas.Registro, true)
                            navControlador.navigate(Rutas.Login)
                        }
                    }
                }, enabled = estado,modifier = Modifier.fillMaxWidth().testTag("boton-registrarse"),)
                Row(
                ) {
                    TextoInformativo(textoId = R.string.cuenta_existe)
                    SpacerHorizontal(tamano = 4.dp)
                    TextoEnlace(
                        textoId = R.string.iniciar_sesion,
                        onClick = {
                            navControlador.popBackStack(Rutas.Registro, true)
                            navControlador.navigate(Rutas.Login)
                        })
                }
                ButtonSecundario(R.string.salir, {
                    activity.finishAffinity()
                }, enabled = estado)
            }
        }
    }
}

