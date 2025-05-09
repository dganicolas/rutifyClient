package com.example.rutifyclient.pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.navigation.Rutas
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SplashScreen(navControlador: NavHostController) {
    LaunchedEffect(Unit) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario == null ) {
            navControlador.navigate(Rutas.Rutina) {
                popUpTo(Rutas.Splash) { inclusive = true }
            }
        } else {
            navControlador.navigate(Rutas.Login) {
                popUpTo(Rutas.Splash) { inclusive = true }
            }
        }
    }
    val isDarkTheme = isSystemInDarkTheme()
    val logoRes = if (isDarkTheme) R.drawable.imagen_fondo_oscuro else R.drawable.imagen_fondo

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = logoRes),
                contentDescription = "Logo",
                modifier = Modifier.width(150.dp).aspectRatio(1f) // Asegura que sea cuadrada antes de recortarla
                .clip(CircleShape)
            )
            SpacerVertical(20.dp)
            TextoTitulo(R.string.nombre_app)
            Spacer(modifier = Modifier.weight(1f))
            TextoInformativo(R.string.copyright)
        }
    }
}