package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.ventanas.ImagenAmpliable
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.utils.obtenerAvatarResource

@Composable
fun TarjetaComentario(
    idFirebase: String,
    puedeEliminarlo: Boolean,
    comentario: ComentarioDto,
    navController: NavController,
    listaEstados: Map<String, String>,
    estado: Boolean = true,
    maxImagen : Dp = 100.dp,
    onClick: (ComentarioDto) -> Unit,
    onAprobar: ((ComentarioDto) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth().clickable { if(estado)navController.navigate("detallesComentario/${comentario._id}") },
        shape = shapes.medium,
        border = BorderStroke(4.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.padding(12.dp).weight(3f)) {
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.Top) {
                    // Avatar
                    Image(
                        painter = painterResource(id = obtenerAvatarResource(comentario.avatarUrl)),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable { navController.navigate("perfil/${comentario.idFirebase}") }
                    )


                    // Nombre y Fecha
                    Column() {
                        TextoSubtitulo(R.string.texto_input, comentario.nombreUsuario)
                        TextoInformativo(R.string.fecha, comentario.fechaPublicacion.toString())
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if(puedeEliminarlo || idFirebase == comentario.idFirebase){
                        Icono(icono = Icons.Default.Delete,descripcion =R.string.icono,onClick = {onClick(comentario)})
                        if(onAprobar != null){
                            Icono(icono = Icons.Default.Check,descripcion =R.string.icono,onClick = {onAprobar(comentario)})
                        }
                    }
                }
                TextoInformativo(R.string.texto_input, listaEstados[comentario.estadoAnimo] ?: comentario.estadoAnimo)
                TextoSubtitulo(textoId = R.string.texto_input, comentario.texto)
            }

            if(comentario.imagenUrl != null){
                Column(modifier = Modifier
                    .weight(1f)
                    .widthIn(max = maxImagen)
                    .clip(RoundedCornerShape(8.dp)).border(2.dp, colorScheme.primary, RoundedCornerShape(8.dp))) {
                    comentario.imagenUrl?.let { url ->
                        ImagenAmpliable(url.toUri())
                    }
                }
            }
        }
    }
}
