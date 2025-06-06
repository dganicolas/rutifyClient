package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.textos.TextoBuscador
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import com.example.rutifyclient.utils.obtenerAvatarResource

@Composable
fun TarjetaBuscadorPersonas(
    usuario: UsuarioBusquedaDto,
    navController: NavController,
    esAdmin: Boolean = false,
    function: (UsuarioBusquedaDto) -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .height(64.dp).fillMaxWidth().clickable { navController.navigate("perfil/${usuario.idFirebase}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = shapes.medium,
        border = BorderStroke(2.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 5.dp)) {
            Image(
                modifier = Modifier.fillMaxHeight(),contentScale = ContentScale.Crop,
                painter = painterResource(id = obtenerAvatarResource(usuario.avatar)),
                contentDescription = "Foto avatar"
            )
            Icon(
                modifier = Modifier.fillMaxHeight().size(38.dp),
                imageVector = if (usuario.sexo == "H") Icons.Filled.Male else Icons.Filled.Female,
                tint = if (usuario.sexo == "H") Color.Blue else Color.Red,
                contentDescription = "Sexo"
            )
            TextoBuscador(usuario.nombre,Modifier.weight(1f))
            if(esAdmin){
                Icono(icono = Icons.Default.Delete,descripcion = R.string.icono,onClick = {function(usuario)})
            }
            if (usuario.esPremium){
                Icon(
                    modifier = Modifier.fillMaxHeight().size(38.dp),
                    imageVector =  Icons.Filled.WorkspacePremium,
                    tint = colorScheme.primary,
                    contentDescription = "Icono Premium"
                )
            }
        }
    }
}