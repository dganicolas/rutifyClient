package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoInput
import com.example.rutifyclient.domain.ComentarioDto
import com.example.rutifyclient.utils.obtenerAvatarResource

@Composable
fun TarjetaComentario(comentario: ComentarioDto,navController:NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = shapes.medium,
        border = BorderStroke(2.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            // Imagen de perfil
            Image(
                painter = painterResource(id = obtenerAvatarResource(comentario.avatarUrl)),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {  }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // Nombre y fecha
                TextoInformativo(R.string.autor,comentario.nombreUsuario)
                TextoInformativo(R.string.fecha,comentario.fechaPublicacion)
                TextoInformativo(R.string.estado,comentario.estadoAnimo)
                SpacerVertical(8.dp)
                TextoInformativo(R.string.comentario,comentario.texto)
            }
        }
    }
}
