package com.example.rutifyclient.pantalla.foro

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.rutifyclient.domain.ComentarioDto
import com.example.rutifyclient.pantalla.foro.utils.PantallaComentarios

@Composable
fun Foro(modifier: Modifier, navControlador: NavHostController) {
    val comentarioEjemplo = ComentarioDto(
        idFirebase = "123",
        nombreUsuario = "Juan Pérez",
        avatarUrl = "avatar1", // Supón que usas un recurso local
        fechaPublicacion = "06/05/2025",
        estadoAnimo = "Motivado",
        texto = "¡Hoy fue un gran día en el gym! Me siento más fuerte que nunca."
    )
    val listaComentarios = List(300) { comentarioEjemplo.copy(idFirebase = it.toString()) }
    Column(modifier = modifier) {
        PantallaComentarios(listaComentarios, navControlador )
    }



}


