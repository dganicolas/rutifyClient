package com.example.rutifyclient.apiservice.network.api.votos

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.voto.VotodDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiVotos {

    @POST("${ApiRoutes.VOTOS}/registrar")
    suspend fun registrarVoto(
        @Body voto: VotodDto
    ): Response<VotodDto>

    @PATCH("${ApiRoutes.VOTOS}/actualizar")
    suspend fun actualizarVoto(
        @Body voto: VotodDto
    ): Response<VotodDto>

    @GET("${ApiRoutes.VOTOS}/obtenerVoto")
    suspend fun obtenerVoto(
        @Query("idFirebase") idFirebase: String,
        @Query("idRutina") idRutina: String
    ): Response<VotodDto>

    @DELETE("${ApiRoutes.VOTOS}/eliminarVoto")
    suspend fun eliminarVoto(
        @Body voto: VotodDto
    ): Response<Void>
}