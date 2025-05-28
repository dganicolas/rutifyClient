package com.example.rutifyclient.apiservice.network.api.ejercicios

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEjercicios {
    @GET("${ApiRoutes.EJERCICIOS}/obteneEjercicios")
    suspend fun obtenerEjercicios(
        @Query("grupoMuscular") grupoMuscular: String? = null,
        @Query("equipo") equipo: String? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): Response<List<EjercicioDto>>

    @GET("${ApiRoutes.EJERCICIOS}/retodiario")
    suspend fun retoDiario(): Response<EjercicioDto>
}