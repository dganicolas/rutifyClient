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
        RutinaBuscadorDto("abc123", "Rutina Full Body", "fb", "Entrenamiento completo para todo el", 8, false, "mancuernas"),
        RutinaBuscadorDto("abc124", "Rutina Piernas", "pn", "Rutina centrada en las piernas", 6, true, "mancuernas"),
        RutinaBuscadorDto("abc125", "Rutina Brazos", "bz", "Trabajo de fuerza para los brazos", 7, false, "mancuernas"),
        RutinaBuscadorDto("abc126", "Rutina Pecho", "pc", "Ejercicios para trabajar el pecho", 5, true, "banco"),
        RutinaBuscadorDto("abc127", "Rutina Espalda", "ed", "Rutina centrada en la espalda", 8, false, "barras"),
        RutinaBuscadorDto("abc128", "Rutina Cardio", "cd", "Entrenamiento cardiovascular", 4, true, "cinta"),
        RutinaBuscadorDto("abc129", "Rutina HIIT", "ht", "Entrenamiento de alta intensidad", 6, false, "ninguno"),
        RutinaBuscadorDto("abc130", "Rutina Abdomen", "am", "Trabajo para el abdomen", 5, true, "banco"),
        RutinaBuscadorDto("abc131", "Rutina Bodyweight", "mn", "Rutina sin equipo", 7, false, "ninguno"),
        RutinaBuscadorDto("abc132", "Rutina Mancuernas", "bz", "Entrenamiento con mancuernas", 6, true, "mancuernas"),
        RutinaBuscadorDto("abc133", "Rutina Full Body 2", "fb", "Entrenamiento completo para todo el cuerpo", 8, false, "barras"),
        RutinaBuscadorDto("abc134", "Rutina Piernas 2", "pn", "Rutina centrada en las piernas", 6, true, "barras"),
        RutinaBuscadorDto("abc135", "Rutina Brazos 2", "bz", "Trabajo de fuerza para los brazos", 7, false, "banco"),
        RutinaBuscadorDto("abc136", "Rutina Pecho 2", "pc", "Ejercicios para trabajar el pecho", 5, true, "mancuernas"),
        RutinaBuscadorDto("abc137", "Rutina Espalda 2", "ed", "Rutina centrada en la espalda", 8, false, "ninguno"),
        RutinaBuscadorDto("abc138", "Rutina Cardio 2", "cd", "Entrenamiento cardiovascular", 4, true, "cinta"),
        RutinaBuscadorDto("abc139", "Rutina HIIT 2", "ht", "Entrenamiento de alta intensidad", 6, false, "ninguno"),
        RutinaBuscadorDto("abc140", "Rutina Abdomen 2", "am", "Trabajo para el abdomen", 5, true, "mancuernas")
    )

    Column(modifier = modifier) {
        PantallaBusquedaRutina(rutinas,navControlador)
    }
}