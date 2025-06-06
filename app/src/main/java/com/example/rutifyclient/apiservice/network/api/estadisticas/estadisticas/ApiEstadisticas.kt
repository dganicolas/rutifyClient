package com.example.rutifyclient.apiservice.network.api.estadisticas.estadisticas

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasPatchDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiEstadisticas {
    @POST("${ApiRoutes.ESTADISTICAS}/crear")
    suspend fun crearEstadisticas(
        @Body estadisticas: EstadisticasDto
    ): Response<EstadisticasDto>

    @GET("${ApiRoutes.ESTADISTICAS}/{usuarioId}")
    suspend fun obtenerEstadisticas(
        @Path("usuarioId") usuarioId: String
    ): Response<EstadisticasDto>

    @PATCH("${ApiRoutes.ESTADISTICAS}/{usuarioId}")
    suspend fun actualizarEstadisticas(
        @Path("usuarioId") usuarioId: String,
        @Body estadisticasActualizadas: EstadisticasPatchDto
    ): Response<EstadisticasDto>
}