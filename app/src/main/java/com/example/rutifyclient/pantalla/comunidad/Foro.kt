package com.example.rutifyclient.pantalla.comunidad

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.ventanas.ImagenAmpliable
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaComentarios
import com.example.rutifyclient.viewModel.ForoViewModel

@Composable
fun Foro(navControlador: NavHostController) {
    val viewModel: ForoViewModel = viewModel()
    val comentarios by viewModel.comentarios.observeAsState(emptyList())
    val mostrarDialogo by viewModel.mostrarDialogoComentario.observeAsState(false)
    val textoComentario by viewModel.textoComentario.observeAsState("")
    val imagenUri by viewModel.imagenUri.observeAsState()

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.setImagenUri(it)
        }
    }
    val cameraUri by viewModel.cameraUri.observeAsState()

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && cameraUri != null) {
            viewModel.setImagenUri(cameraUri!!)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
        viewModel.obtenerComentarios()
    }

    PantallaBase(
        viewModel = viewModel(),
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.Comunidad) }),
        iconoFloatingButton = Icons.Default.AddComment,
        onClickFloatingButton = { viewModel.abrirDialogoComentario() }
    ) {
        if (mostrarDialogo) {
            TarjetaNormal(modifier= Modifier.padding(it).zIndex(1f)) {
                Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
                    Icono(
                        descripcion = R.string.icono,
                        icono = Icons.Default.Close,
                        onClick = { viewModel.cerrarDialogoComentario() })
                    CampoTexto(
                        value = textoComentario,
                        onValueChange = { viewModel.setTextoComentario(it) },
                        textoIdLabel = R.string.escribeNuevoComentario
                    )
                    Row {
                        ButtonPrincipal(modifier = Modifier.weight(1f), textoId = R.string.subirImagen, onClick = {
                            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                android.Manifest.permission.READ_MEDIA_IMAGES
                            } else {
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            }

                            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                                launcher.launch("image/*")
                            } else {
                                ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 0)
                            }
                        })
                        ButtonPrincipal(modifier = Modifier.weight(1f), textoId = R.string.hacerFoto,  onClick = {
                            val permission = android.Manifest.permission.CAMERA

                            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                                viewModel.crearUriParaFoto(context)
                                takePictureLauncher.launch(cameraUri!!)
                            } else {
                                ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 0)
                            }
                        })
                    }
                    ImagenAmpliable(imagenUri)
                    ButtonPrincipal(R.string.publicar, onClick = { viewModel.crearComentario(context) })
                    ButtonSecundario(
                        R.string.cancelar,
                        onClick = { viewModel.cerrarDialogoComentario() })
                }
            }
        }else
        Column(modifier = Modifier.padding(it)) {
            PantallaComentarios(comentarios, navControlador)
        }

    }


}


