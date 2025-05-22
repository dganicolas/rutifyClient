package com.example.rutifyclient.utils

import androidx.lifecycle.MutableLiveData
import com.example.rutifyclient.domain.ejercicio.EjercicioDto

object ente {
    val listaEjercicio = MutableLiveData<List<EjercicioDto>>(emptyList())
    val rutina = MutableLiveData<String>("")
}