package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.acordeon.Acordeon
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.RutinaDTO
import com.example.rutifyclient.pantalla.barScaffolding.PantallaConBarraSuperiorRutinas
import com.example.rutifyclient.utils.obtenerIconoRutina

@Composable
fun PantallaDetallesRutinas(idRutina: String) {
    val rutina = RutinaDTO(
        nombre = "Rutinas de hombros",
        imagen = "bz",
        descripcion = "Rutina para principiantes de hombros rapida y efectitiva",
        creadorId = "123xdc",
        ejercicios = mapOf("123xd" to 3),
        equipo = "mancuerna,esterilla",
        esPremium = false
    )
    PantallaConBarraSuperiorRutinas({ }, { }) { innerPading ->
        Box(
            modifier = Modifier.padding(
                top = innerPading.calculateTopPadding() + 5.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = innerPading.calculateBottomPadding() + 5.dp)
        ) {
            RutinasCard {
                TextoTitulo(R.string.titulo_rutina_detalle,rutina.nombre)
                Icon(
                    modifier = Modifier
                        .size(240.dp)  // Tamaño ajustado
                        .clip(RoundedCornerShape(8.dp)),  // Esquinas redondeadas
                    imageVector = obtenerIconoRutina(rutina.imagen),  // Usamos el método que asigna los iconos
                    contentDescription = "Imagen de la rutina",
                )
                Acordeon(R.string.titulo_descripcion,"holi")
                Acordeon(R.string.titulo_ejercicios,"holi")
                Acordeon(R.string.titulo_equipo,"holi")
                ButtonPrincipal(R.string.empezar,{})
            }
        }
    }
}

@Preview
@Composable
fun preview23() {
    PantallaDetallesRutinas("1")
}

