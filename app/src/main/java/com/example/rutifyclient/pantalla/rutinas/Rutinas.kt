package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.rutifyclient.componentes.tarjetas.RutinaBuscadorDto
import com.example.rutifyclient.pantalla.comunidad.utils.PantallaBusquedaRutina

@Composable
fun Rutinas(modifier: Modifier, navControlador: NavHostController) {
    val rutinas = listOf(
        RutinaBuscadorDto(
            id = "abc123",
            nombre = "Rutina Full Body",
            imagen = "ic_full_body",
            descripcion = "Entrenamiento completo para todo el",
            cuantosEjercicios = 8,
            esPremium = false,
            equipo = "mancuernas"
        ),
        RutinaBuscadorDto(
            id = "abc124",
            nombre = "Rutina Piernas",
            imagen = "ic_piernas",
            descripcion = "Rutina centrada en las piernas",
            cuantosEjercicios = 6,
            esPremium = true,
            equipo = "mancuernas"
        ),
        RutinaBuscadorDto(
            id = "abc125",
            nombre = "Rutina Brazos",
            imagen = "ic_brazos",
            descripcion = "Trabajo de fuerza para los brazos",
            cuantosEjercicios = 7,
            esPremium = false,
            equipo = "mancuernas"
        ),
        RutinaBuscadorDto(
            id = "abc126",
            nombre = "Rutina Pecho",
            imagen = "ic_pecho",
            descripcion = "Ejercicios para trabajar el pecho",
            cuantosEjercicios = 5,
            esPremium = true,
            equipo = "banco"
        ),
        RutinaBuscadorDto(
            id = "abc127",
            nombre = "Rutina Espalda",
            imagen = "ic_espalda",
            descripcion = "Rutina centrada en la espalda",
            cuantosEjercicios = 8,
            esPremium = false,
            equipo = "barras"
        ),
        RutinaBuscadorDto(
            id = "abc128",
            nombre = "Rutina Cardio",
            imagen = "ic_cardio",
            descripcion = "Entrenamiento cardiovascular",
            cuantosEjercicios = 4,
            esPremium = true,
            equipo = "cinta"
        ),
        RutinaBuscadorDto(
            id = "abc129",
            nombre = "Rutina HIIT",
            imagen = "ic_hiit",
            descripcion = "Entrenamiento de alta intensidad",
            cuantosEjercicios = 6,
            esPremium = false,
            equipo = "ninguno"
        ),
        RutinaBuscadorDto(
            id = "abc130",
            nombre = "Rutina Abdomen",
            imagen = "ic_abdomen",
            descripcion = "Trabajo para el abdomen",
            cuantosEjercicios = 5,
            esPremium = true,
            equipo = "banco"
        ),
        RutinaBuscadorDto(
            id = "abc131",
            nombre = "Rutina Bodyweight",
            imagen = "ic_bodyweight",
            descripcion = "Rutina sin equipo",
            cuantosEjercicios = 7,
            esPremium = false,
            equipo = "ninguno"
        ),
        RutinaBuscadorDto(
            id = "abc132",
            nombre = "Rutina Mancuernas",
            imagen = "ic_mancuernas",
            descripcion = "Entrenamiento con mancuernas",
            cuantosEjercicios = 6,
            esPremium = true,
            equipo = "mancuernas"
        ),
        RutinaBuscadorDto(
            id = "abc133",
            nombre = "Rutina Full Body 2",
            imagen = "ic_full_body",
            descripcion = "Entrenamiento completo para todo el cuerpo",
            cuantosEjercicios = 8,
            esPremium = false,
            equipo = "barras"
        ),
        RutinaBuscadorDto(
            id = "abc134",
            nombre = "Rutina Piernas 2",
            imagen = "ic_piernas",
            descripcion = "Rutina centrada en las piernas",
            cuantosEjercicios = 6,
            esPremium = true,
            equipo = "barras"
        ),
        RutinaBuscadorDto(
            id = "abc135",
            nombre = "Rutina Brazos 2",
            imagen = "ic_brazos",
            descripcion = "Trabajo de fuerza para los brazos",
            cuantosEjercicios = 7,
            esPremium = false,
            equipo = "banco"
        ),
        RutinaBuscadorDto(
            id = "abc136",
            nombre = "Rutina Pecho 2",
            imagen = "ic_pecho",
            descripcion = "Ejercicios para trabajar el pecho",
            cuantosEjercicios = 5,
            esPremium = true,
            equipo = "mancuernas"
        ),
        RutinaBuscadorDto(
            id = "abc137",
            nombre = "Rutina Espalda 2",
            imagen = "ic_espalda",
            descripcion = "Rutina centrada en la espalda",
            cuantosEjercicios = 8,
            esPremium = false,
            equipo = "ninguno"
        ),
        RutinaBuscadorDto(
            id = "abc138",
            nombre = "Rutina Cardio 2",
            imagen = "ic_cardio",
            descripcion = "Entrenamiento cardiovascular",
            cuantosEjercicios = 4,
            esPremium = true,
            equipo = "cinta"
        ),
        RutinaBuscadorDto(
            id = "abc139",
            nombre = "Rutina HIIT 2",
            imagen = "ic_hiit",
            descripcion = "Entrenamiento de alta intensidad",
            cuantosEjercicios = 6,
            esPremium = false,
            equipo = "ninguno"
        ),
        RutinaBuscadorDto(
            id = "abc140",
            nombre = "Rutina Abdomen 2",
            imagen = "ic_abdomen",
            descripcion = "Trabajo para el abdomen",
            cuantosEjercicios = 5,
            esPremium = true,
            equipo = "mancuernas"
        )
    )

    Column(modifier = modifier) {
        PantallaBusquedaRutina(rutinas,navControlador)
    }
}