package com.example.rutifyclient.interfaces

import androidx.lifecycle.MutableLiveData
import java.time.LocalDate
import java.util.Date

interface IRegistroViewModel {
    fun registrarUsuario(onResultado: (Boolean) -> Unit)
}