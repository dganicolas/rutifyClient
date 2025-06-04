package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.deslizables.RatingBarVisual
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoInput
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.utils.obtenerIconoRutina

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBusquedaRutinaLocal(
    rutinas: List<RutinaDTO>,
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
                TarjetaRutinaBuscadorLocal(rutinas, navController)
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

@Composable
fun TarjetaRutinaBuscadorLocal(rutina: RutinaDTO, navController: NavController) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("rutinas/${rutina.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = shapes.medium,
        border = BorderStroke(4.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        SpacerVertical(8.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            if (rutina.esPremium) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.WorkspacePremium,
                    tint = colorScheme.primary,
                    contentDescription = "Icono Premium"
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                TextoInput(
                    texto = rutina.nombre,
                    style = typography.titleLarge,
                    color = colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            RatingBarVisual(rutina.votos / rutina.totalVotos)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = 10.dp,
            )
        ) {
            Icono(
                modifier = Modifier
                    .size(60.dp)  // Tamaño ajustado
                    .clip(RoundedCornerShape(8.dp)),  // Esquinas redondeadas
                imagen = obtenerIconoRutina(rutina.imagen),  // Usamos el método que asigna los iconos
                descripcion = R.string.descripcionIconoRutina,
                onClick = {
                    navController.navigate("rutinas/${rutina.id}")
                }
            )

            Spacer(modifier = Modifier.width(10.dp))  // Espaciado entre la imagen y el texto

            Column(
                modifier = Modifier.weight(1f)
            ) {
                SpacerVertical(5.dp)

                SpacerVertical(5.dp)
                TextoInformativo(
                    R.string.equipo, rutina.equipo
                )

            }
            SpacerVertical(5.dp)

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            TextoInformativo(
                R.string.descripcion, if (rutina.descripcion.length > 100) rutina.descripcion.take(100) + "..." else rutina.descripcion,
            )
        }
        SpacerVertical(8.dp)
    }
}