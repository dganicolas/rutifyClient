package com.example.rutifyclient.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.TarjetaRutinaBuscador
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBusquedaRutina(
    rutinas: List<RutinaBuscadorDto>,
    estado: Boolean,
    navController: NavController,
    alLlegarAlFinal: () -> Unit,
    alRefrescar: () -> Unit,
) {
    val listState = rememberLazyListState()
    val estadoRefrescar = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = Modifier.fillMaxWidth(),
        state = estadoRefrescar,
        isRefreshing = !estado,
        onRefresh = {alRefrescar()}
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(5.dp),
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

}

