package com.example.rutifyclient.pantalla.foro.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.TarjetaRutinaBuscador
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto

@Composable
fun PantallaBusquedaRutina(rutinas: List<RutinaBuscadorDto>, navController: NavController, alLlegarAlFinal: () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(5.dp),
        state = listState
    ) {
        items(rutinas, key = { it.id!! }) { rutinas ->
            TarjetaRutinaBuscador(rutinas, navController)
            SpacerVertical(5.dp)
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisible >= totalItems - 1
        }.collect { isAtEnd ->
            if (isAtEnd) {
                alLlegarAlFinal()
            }
        }
    }
}