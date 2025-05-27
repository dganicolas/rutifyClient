package com.example.rutifyclient.apiservice.network.api.estadisticas.estadisticasDiarias

import com.example.rutifyclient.apiservice.network.ApiRoutes.ESTADISTICASDIARIAS
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasPatchDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import java.time.LocalDate

interface ApiEstadisticasDiarias {
    @GET("${ESTADISTICASDIARIAS}/mes")
    suspend fun obtenerEstadisticasDiariasDeUnMes(
        @Query("idFirebase") idFirebase: String,
        @Query("fecha") fecha: String
    ): Response<List<EstadisticasDiariasDto>>

    @PATCH(ESTADISTICASDIARIAS)
    suspend fun actualizarEstadisticasDiarias(
        @Query("idFirebase") idFirebase: String,
        @Query("fecha") fecha: String,
        @Body patch: EstadisticasDiariasPatchDto
    ): Response<EstadisticasDiariasDto>

    @GET("${ESTADISTICASDIARIAS}/ultimosPesos")
    suspend fun obtenerUltimos5Pesos(
        @Query("idFirebase") idFirebase: String
    ): Response<List<Double>>

    @GET("${ESTADISTICASDIARIAS}/dia")
    suspend fun obtenerEstadisticasDiariasDia(
        @Query("idFirebase") idFirebase: String,
        @Query("fecha") fecha: LocalDate
    ): Response<EstadisticasDiariasDto?>
}