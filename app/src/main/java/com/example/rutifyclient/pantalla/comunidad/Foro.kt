package com.example.rutifyclient.pantalla.comunidad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.utils.PantallaComentarios

@Composable
fun Foro(navControlador: NavHostController) {
    val comentarioEjemplo = ComentarioDto(
        idFirebase = "123",
        nombreUsuario = "Juan Pérez",
        avatarUrl = "avatar1", // Supón que usas un recurso local
        fechaPublicacion = "06/05/2025",
        estadoAnimo = "Motivado",
        texto = "¡Hoy fue un gran día en el gym! Me siento más fuerte que nunca."
    )
    val listaComentarios = List(300) { comentarioEjemplo.copy(idFirebase = it.toString()) }
    PantallaBase(
        viewModel = viewModel(),
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.MiZona) })
    ) {
        Column(modifier = Modifier.padding(it)) {
            PantallaComentarios(listaComentarios, navControlador)
        }
    }


}


