package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoInput
import com.example.rutifyclient.utils.obtenerIconoRutina

data class RutinaBuscadorDto(
    val id: String? = null,
    val nombre: String,
    val imagen: String,
    val descripcion: String,
    val cuantosEjercicios: Int,
    val esPremium: Boolean,
    val equipo:String,
)

@Composable
fun TarjetaRutinaBuscador(rutina: RutinaBuscadorDto,navController: NavController) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .size(120.dp)
            .clickable {
                navController.navigate("rutinas/${rutina.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = shapes.medium,
        border = BorderStroke(2.dp, colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)  // Ajustado para mejorar el espaciado
        ) {
            // Mostrar la imagen representativa de la rutina
            Icon(
                modifier = Modifier
                    .size(60.dp)  // Tamaño ajustado
                    .clip(RoundedCornerShape(8.dp)),  // Esquinas redondeadas
                imageVector = obtenerIconoRutina(rutina.imagen),  // Usamos el método que asigna los iconos
                contentDescription = "Imagen de la rutina",
            )

            Spacer(modifier = Modifier.width(10.dp))  // Espaciado entre la imagen y el texto

            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextoInput(
                    texto = rutina.nombre,
                    style = typography.titleLarge,
                    color = colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SpacerVertical(5.dp)
                TextoInformativo(
                    R.string.descripcion, rutina.descripcion,
                )
                SpacerVertical(5.dp)
                TextoInformativo(
                    R.string.equipo, rutina.equipo
                )

            }
            SpacerVertical(5.dp)
            // Icono Premium si aplica
            Column {
                TextoInformativo(
                    R.string.puntuacion, rutina.cuantosEjercicios
                )
                if (rutina.esPremium) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Filled.WorkspacePremium,
                        tint = colorScheme.primary,
                        contentDescription = "Icono Premium"
                    )
                }else{
                    SpacerVertical(30.dp)
                }
            }
        }
    }
}