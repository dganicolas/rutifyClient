package com.example.rutifyclient.utils

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.tarjetas.TarjetaBuscadorPersonas
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp


@Composable
fun PantallaBusquedaUsuarios(usuarios: List<UsuarioBusquedaDto>, navController: NavController) {
    LazyColumn(
    ) {
        items(usuarios, key = { usuario -> usuario.idFirebase }) { usuario ->
            TarjetaBuscadorPersonas(usuario, navController)
            SpacerVertical(3.dp)
        }
    }
}