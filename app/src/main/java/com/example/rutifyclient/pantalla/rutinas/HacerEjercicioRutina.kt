package com.example.rutifyclient.pantalla.rutinas

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.Ejercicio
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.net.URLDecoder


@Composable
fun HacerEjercicioRutina(ejercicioIds: String) {
    val gson = Gson()
    Log.i("xd", ejercicioIds)
    val ejercicios = gson.fromJson(URLDecoder.decode(ejercicioIds, "UTF-8"), Array<Ejercicio>::class.java).toList()
    Log.i("1", ejercicios.toString())
        // Aquí puedes hacer algo con la clave (key) y el valor (value)
        TextoTitulo(R.string.comentario,ejercicios[0].nombreEjercicio)

}
