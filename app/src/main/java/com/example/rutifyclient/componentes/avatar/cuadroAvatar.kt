package com.example.rutifyclient.componentes.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.Indumentaria
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.utils.obtenerAvatarResource
import java.time.LocalDate

@Composable
fun cuadroAvatar(usuario: UsuarioInformacionDto, cambiarCara: () -> Unit = {}, cambiarRopa: () -> Unit = {}) {
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
                    estado = false,
                    descripcion = R.string.avatarInfo,
                    onClick = {cambiarCara()},
                    modifier = Modifier
                        .align(Alignment.Center).offset(y = (-95).dp).zIndex(1f).size(65.dp)
                )

                // Cuerpo justo debajo de la cara
                Icono(
                    imagen = rememberAsyncImagePainter(model = usuario.indumentaria.camiseta),
                    estado = false,
                    descripcion = R.string.avatarInfo,
                    onClick = {cambiarRopa()},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-40).dp).size(95.dp).zIndex(0.6f)
                )
                Icono(
                    imagen = rememberAsyncImagePainter(model = usuario.indumentaria.colorPiel),
                    estado = false,
                    descripcion = R.string.avatarInfo,
                    onClick = {cambiarRopa()},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-35).dp, x= 3.dp).size(110
                            .dp)
                )
                Icono(
                    imagen = rememberAsyncImagePainter(model = usuario.indumentaria.pantalon),
                    estado = false,
                    descripcion = R.string.avatarInfo,
                    onClick = {cambiarRopa()},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 50.dp, x= 0.dp).size(120.dp).zIndex(0.4f) // Ajusta este valor según el tamaño de tus íconos
                )
                Icono(
                    imagen = rememberAsyncImagePainter(model = usuario.indumentaria.tenis),
                    descripcion = R.string.avatarInfo,
                    estado = false,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 117.dp, x= (-0.8).dp).size(67.dp).zIndex(0.3f) // Ajusta este valor según el tamaño de tus íconos
                )
                TarjetaNormal(modifier = Modifier
                    .align(Alignment.Center)) {
                    Column(modifier = Modifier.zIndex(1f).padding(5.dp)) {
                        TextoInformativo(R.string.monedas, usuario.monedas)
                        TextoInformativo(R.string.lvlbrazo, usuario.estadisticas.lvlBrazo)
                        TextoInformativo(R.string.lvlPecho, usuario.estadisticas.lvlPecho)
                        TextoInformativo(R.string.lvlAbdominal, usuario.estadisticas.lvlAbdominal)
                        TextoInformativo(R.string.lvlEspalda, usuario.estadisticas.lvlEspalda)
                        TextoInformativo(R.string.lvlPiernas, usuario.estadisticas.lvlPiernas)
                        TextoInformativo(R.string.ejerciciosRealizados, usuario.estadisticas.ejerciciosRealizados)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun pre(){
    val usuarioEjemplo = UsuarioInformacionDto(
        idFirebase = "Um60CAz9ClWxzL0eIkN4QZFMbCB2",
        sexo = "H",
        nombre = "nicolas",
        correo = "nicolasdgomar@gmail.com",
        avatarUrl = "",
        countComentarios = 0,
        countRutinas = 0,
        countVotos = 0,
        fechaUltimoReto = LocalDate.now(),
        esPremium = false,
        estadisticas = EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0.0),
        indumentaria = Indumentaria(
            colorPiel = "https://i.ibb.co/mkfD3hj/brazos-1.webp",
            camiseta = "https://i.ibb.co/ccLWvVh3/camisetaavatar-2.webp",
            pantalon = "https://i.postimg.cc/pdyyJWQ0/pantalonavatar.webp",
            tenis = "https://i.ibb.co/Y7KVHNKC/zapatoavatar.webp"
        )
    )
    Column(
        Modifier.fillMaxSize()
    ) {
        cuadroAvatar(usuario = usuarioEjemplo,{})
    }
}