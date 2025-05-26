package com.example.rutifyclient.componentes.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.utils.obtenerAvatarResource

@Composable
fun cuadroAvatar(usuario: UsuarioInformacionDto) {
    Box(modifier = Modifier.fillMaxHeight(0.40f)) {
        TarjetaNormal(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Imagen de fondo
                Image(
                    painter = painterResource(id = R.drawable.imagen_fondo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Cara centrada
                Icono(
                    imagen = painterResource(id = obtenerAvatarResource(usuario.avatarUrl)),
                    descripcion = R.string.avatarInfo,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center).offset(y = (-55).dp).zIndex(1f).size(65.dp)
                )

                // Cuerpo justo debajo de la cara
                Icono(
                    imagen = painterResource(id = R.drawable.camisetaavatar),
                    descripcion = R.string.avatarInfo,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-5).dp).size(95.dp).zIndex(0.6f)
                )

                Icono(
                    imagen = painterResource(id = R.drawable.brazopng),
                    descripcion = R.string.avatarInfo,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 5.dp, x= (-30).dp).size(70.dp).graphicsLayer {
                            scaleX = -1f
                        }
                )
                Icono(
                    imagen = painterResource(id = R.drawable.brazopng),
                    descripcion = R.string.avatarInfo,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 5.dp, x= 30.dp).size(70.dp)
                )
                Icono(
                    imagen = painterResource(id = R.drawable.pantalonavatar),
                    descripcion = R.string.avatarInfo,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 70.dp, x= 0.dp).size(120.dp).zIndex(0.4f) // Ajusta este valor según el tamaño de tus íconos
                )
                Icono(
                    imagen = painterResource(id = R.drawable.zapatoavatar),
                    descripcion = R.string.avatarInfo,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 140.dp, x= 0.dp).size(68.dp).zIndex(0.3f) // Ajusta este valor según el tamaño de tus íconos
                )
            }
        }
    }
}

@Preview
@Composable
fun prev(){
    Box(modifier = Modifier.fillMaxSize()){
        cuadroAvatar(UsuarioInformacionDto(
            "", "", "", "", false, "", EstadisticasDto("", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0), 0
        ))
    }
}