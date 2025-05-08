package com.example.rutifyclient.pantalla

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.viewModel.RegistroViewModel

@Composable
fun Registro(navControlador: NavHostController) {
    val viewModel: RegistroViewModel = viewModel()
    val nombre by viewModel.textoNombre.observeAsState("")
    val fechaNacimiento by viewModel.textoFechaNacimiento.observeAsState("")
    val correo by viewModel.correo.observeAsState("")
    val mensajeToast by viewModel.mensajeToast.observeAsState(R.string.dato_defecto)
    val toastMostrado by viewModel.toastMostrado.observeAsState(true)
    val opcionesSexo by viewModel.opcionesSexo.observeAsState(listOf())
    val opcionEscogida by viewModel.opcionEscogida.observeAsState(0)
    val checboxTerminos by viewModel.checboxTerminos.observeAsState(false)
    val contrasena by viewModel.contrasena.observeAsState("")
    val mostrarContrasena by viewModel.mostrarContrasena.observeAsState(false)
    val contrasenaConfirmacion by viewModel.contrasenaConfirmacion.observeAsState("")
    val context = LocalContext.current
    val activity = context as Activity
    LaunchedEffect(mensajeToast) {
        if (!toastMostrado) {
            Toast.makeText(context, mensajeToast, Toast.LENGTH_LONG).show()
            viewModel.toastMostrado()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Agregar un poco de padding
        contentAlignment = Alignment.Center // Centra el contenido
    ) {
        FormularioCard(R.string.nueva_cuenta) {
            TextoSubtitulo(textoId = R.string.registro_info)
            CampoTexto(
                value = nombre,
                onValueChange = { viewModel.cambiarNombre(it) },
                textoIdLabel = R.string.nombre,
            )

            CampoTexto(
                value = correo,
                onValueChange = { viewModel.cambiarCorreo(it) },
                textoIdLabel = R.string.correo,
            )

            CampoTexto(
                value = contrasena,
                onValueChange = { viewModel.cambiarContrasena(it) },
                textoIdLabel = R.string.contrasena,
                visualTransformation = if (mostrarContrasena) PasswordVisualTransformation() else VisualTransformation.None,
                icono = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                modifierIcon = Modifier
                    .clickable { viewModel.mostrarContrasena(!mostrarContrasena) },
            )

            CampoTexto(
                value = contrasenaConfirmacion,
                onValueChange = { viewModel.cambiarContrasenaConfirmacion(it) },
                textoIdLabel = R.string.contrasena_confirmacion,
                visualTransformation = if (mostrarContrasena) PasswordVisualTransformation() else VisualTransformation.None,
                icono = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                modifierIcon = Modifier
                    .clickable { viewModel.mostrarContrasena(!mostrarContrasena) },
            )

            SelectorFechaNacimiento(
                fechaNacimiento = fechaNacimiento,
                onFechaSeleccionada = { viewModel.cambiarFechaNacimiento(it) },
                onFechaInvalida = {
                    viewModel.mostrarToast(R.string.fecha_invalida)
                }
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
                { viewModel.mostrarToast(R.string.toast_terminos) }
            )

            ButtonPrincipal(textoId = R.string.registrarse, {
                viewModel.registrarUsuario() {
                    if (it) {
                        navControlador.popBackStack(Rutas.Registro, true)
                        navControlador.navigate(Rutas.Login)
                    }
                }
            })
            Row(
            ) {
                TextoInformativo(textoId = R.string.cuenta_existe)
                SpacerHorizontal(tamano = 4.dp)
                TextoEnlace(
                    textoId = R.string.registrarse,
                    onClick = {
                        navControlador.popBackStack(Rutas.Registro, true)
                        navControlador.navigate(Rutas.Login)
                    })
            }
            ButtonSecundario(R.string.salir, {
                activity.finishAffinity()
            })
        }
    }
}

