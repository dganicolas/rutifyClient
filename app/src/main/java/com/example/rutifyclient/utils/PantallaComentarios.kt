package com.example.rutifyclient.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.TarjetaAnuncio
import com.example.rutifyclient.componentes.tarjetas.TarjetaComentario
import com.example.rutifyclient.domain.comentario.ComentarioDto

@Composable
fun PantallaComentarios(
    idFirebase: String,
    puedeEliminarlo: Boolean,
    onEliminar: (ComentarioDto) -> Unit,
    listaComentarios: List<ComentarioDto>,
    navController: NavController,
    listaEstados: Map<String, String>,
    estado: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 16.dp),
    maxImagen: Dp = 100.dp,
) {
    LazyColumn(
        modifier = modifier
    ) {
        if(estado){
            item {
                TarjetaAnuncio(
                    context = LocalContext.current,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                SpacerVertical(10.dp)
            }
        }
        items(listaComentarios, key = { comentario -> comentario._id!! }) { comentario ->
            TarjetaComentario(idFirebase,
                puedeEliminarlo,
                comentario,
                navController,
                listaEstados,
                estado,
                maxImagen,
                { onEliminar(it) })
            SpacerVertical(10.dp)
        }
    }
}