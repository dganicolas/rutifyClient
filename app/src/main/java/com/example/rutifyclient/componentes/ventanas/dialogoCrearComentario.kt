package com.example.rutifyclient.componentes.ventanas

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.botones.ButtonSecundario
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dialogoCrearComentario(
    textoComentario: String,
    cerrarDialogoComentario: () -> Unit,
    setTextoComentario: (String) -> Unit,
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    imagenUri: Uri?,
    crearComentario: () -> Unit,
    context: Context,
    crearUriParaFoto: () -> Unit,
    expanded: Boolean,
    expandir: (Boolean) -> Unit,
    estadoActual: String,
    listaEstados: Map<String,String>,
    setEstadoAnimo: (String) -> Unit,
    estado:Boolean

) {
    TarjetaNormal(modifier = Modifier
        .zIndex(1f)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)) {
            CampoTexto(
                modifier = Modifier
                    .fillMaxWidth().heightIn(min = 120.dp, max = 150.dp) // Ajusta el alto como prefieras
                    .verticalScroll(rememberScrollState()),
                value = textoComentario,
                onValueChange = { setTextoComentario(it) },
                textoIdLabel = R.string.escribeComentario, // crea en strings.xml
                singleLine = false,
                maxLines = 4
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expandir(!expanded) }
            ) {
                OutlinedTextField(
                    value = listaEstados[estadoActual]!!,
                    onValueChange = {},
                    readOnly = true,
                    label = { TextoInformativo(R.string.estadoAnimo) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expandir(false) }
                ) {
                    listaEstados.forEach { (codigo, descripcion) ->
                        DropdownMenuItem(
                            text = { TextoSubtitulo(R.string.texto_input,descripcion) },
                            onClick = {
                                setEstadoAnimo(codigo)
                                expandir(false)
                            }
                        )
                    }
                }
            }
            Row {
                ButtonPrincipal(
                    modifier = Modifier.weight(1f),
                    textoId = R.string.subirImagen,
                    onClick = {
                        val permission =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                android.Manifest.permission.READ_MEDIA_IMAGES
                            } else {
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            }

                        if (ContextCompat.checkSelfPermission(
                                context,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            launcher.launch("image/*")
                        } else {
                            ActivityCompat.requestPermissions(
                                context as Activity,
                                arrayOf(permission),
                                0
                            )
                        }
                    },
                    enabled = estado)
                ButtonPrincipal(
                    modifier = Modifier.weight(1f),
                    textoId = R.string.hacerFoto,
                    onClick = {
                        val permission = android.Manifest.permission.CAMERA

                        if (ContextCompat.checkSelfPermission(
                                context,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            crearUriParaFoto()
                        } else {
                            ActivityCompat.requestPermissions(
                                context as Activity,
                                arrayOf(permission),
                                0
                            )
                        }
                    },
                    enabled = estado)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                ImagenAmpliable(imagenUri)
            }
            ButtonPrincipal(R.string.publicar, onClick = { crearComentario() },
                enabled = estado)
            ButtonSecundario(
                R.string.cancelar,
                onClick = { cerrarDialogoComentario() },
                enabled = estado)
        }
    }
}