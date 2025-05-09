package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.acordeon.Acordeon
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.Ejercicio
import com.example.rutifyclient.domain.RutinaDTO
import com.example.rutifyclient.pantalla.barScaffolding.PantallaConBarraSuperiorRutinas
import com.example.rutifyclient.utils.obtenerIconoRutina
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PantallaDetallesRutinas(idRutina: String,navControlador:NavController) {
    val rutina = RutinaDTO(
        nombre = "Rutinas de hombros",
        imagen = "bz",
        descripcion = "Rutina para principiantes de hombros rapida y efectitiva",
        creadorId = "123xdc",
        ejercicios = listOf(
            Ejercicio(
                id = "1",
                nombreEjercicio = "Flexiones",
                descripcion = "Ejercicio para fortalecer los músculos del pecho y los tríceps.",
                imagen = "https://i0.wp.com/www.strengthlog.com/wp-content/uploads/2020/02/Push-up.gif?resize=600%2C600&ssl=1",
                equipo = "Ninguno",
                grupoMuscular = "Pecho, Tríceps",
                caloriasQuemadasPorRepeticion = 0.5,
                puntoGanadosPorRepeticion = 1.0,
                cantidad = 3
            ),
            Ejercicio(
                id = "2",
                nombreEjercicio = "Sentadillas",
                descripcion = "Ejercicio para trabajar los músculos de las piernas y glúteos.",
                imagen = "https://i0.wp.com/www.strengthlog.com/wp-content/uploads/2020/02/Squat.gif?resize=600%2C600&ssl=1",
                equipo = "Ninguno",
                grupoMuscular = "Piernas, Glúteos",
                caloriasQuemadasPorRepeticion = 0.4,
                puntoGanadosPorRepeticion = 1.0,
                cantidad = 2
            ),
            Ejercicio(
                id = "3",
                nombreEjercicio = "Plancha",
                descripcion = "Ejercicio isométrico para fortalecer el core y los músculos abdominales.",
                imagen = "https://i0.wp.com/www.strengthlog.com/wp-content/uploads/2020/02/Plank.gif?resize=600%2C600&ssl=1",
                equipo = "Ninguno",
                grupoMuscular = "Abdominales, Core",
                caloriasQuemadasPorRepeticion = 0.3,
                puntoGanadosPorRepeticion = 0.8,
                cantidad = 4
            ),
            Ejercicio(
                id = "4",
                nombreEjercicio = "Dominadas",
                descripcion = "Ejercicio para fortalecer la espalda y los bíceps.",
                imagen = "https://i0.wp.com/www.strengthlog.com/wp-content/uploads/2020/02/Pull-up.gif?resize=600%2C600&ssl=1",
                equipo = "Barras",
                grupoMuscular = "Espalda, Bíceps",
                caloriasQuemadasPorRepeticion = 0.6,
                puntoGanadosPorRepeticion = 1.2,
                cantidad = 5
            )),
        equipo = "mancuerna,esterilla",
        esPremium = false
    )
    val gson = Gson()
    val ejerciciosJson = gson.toJson(rutina.ejercicios)
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
                Acordeon(R.string.titulo_descripcion,rutina.descripcion)
                Acordeon(R.string.titulo_ejercicios, "aregla esto")
                Acordeon(R.string.titulo_equipo,rutina.equipo)
                ButtonPrincipal(R.string.empezar,{navControlador.navigate("ejercicio/${URLEncoder.encode(ejerciciosJson, StandardCharsets.UTF_8.toString())}")})
            }
        }
    }
}

