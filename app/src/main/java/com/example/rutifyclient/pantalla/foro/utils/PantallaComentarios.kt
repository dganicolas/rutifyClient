package com.example.rutifyclient.pantalla.foro.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.TarjetaComentario
import com.example.rutifyclient.domain.ComentarioDto

@Composable
fun PantallaComentarios(listaComentarios:List<ComentarioDto>,navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        items(listaComentarios, key = { comentario -> comentario.idFirebase }) { comentario ->
            TarjetaComentario(comentario, navController)
            SpacerVertical(10.dp)
        }
    }
}