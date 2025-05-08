package com.example.rutifyclient.pantalla.comunidad.utils

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.RutinaBuscadorDto
import com.example.rutifyclient.componentes.tarjetas.TarjetaRutinaBuscador

@Composable
fun PantallaBusquedaRutina(rutinas: List<RutinaBuscadorDto>, navController: NavController) {
    LazyColumn(
    ) {
        items(rutinas, key = { rutina -> rutina.id!! }) { usuario ->
            TarjetaRutinaBuscador(usuario, navController)
            SpacerVertical(10.dp)
        }
    }
}