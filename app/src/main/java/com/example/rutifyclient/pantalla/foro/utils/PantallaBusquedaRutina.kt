package com.example.rutifyclient.pantalla.foro.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.RutinaBuscadorDto
import com.example.rutifyclient.componentes.tarjetas.TarjetaRutinaBuscador

@Composable
fun PantallaBusquedaRutina(rutinas: List<RutinaBuscadorDto>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(rutinas, key = { rutina -> rutina.id!! }) { usuario ->
            TarjetaRutinaBuscador(usuario, navController)
            SpacerVertical(5.dp)
        }
    }
}