package com.example.rutifyclient.apiservice.network.api.rutinas

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRutinas {
    @GET("${ApiRoutes.RUTINAS}/verRutinas")
    suspend fun verRutinas(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("equipo") equipo: String? = null
    ): Response<List<RutinaBuscadorDto>>

    @POST("${ApiRoutes.RUTINAS}/crear")
    suspend fun crearRutina(
        @Body rutinaDTO: RutinaDTO
    ): Response<RutinaDTO>

    @GET("${ApiRoutes.RUTINAS}/{idRutina}")
    suspend fun obtenerRutina(
        @Path("idRutina") idRutina: String
    ): Response<RutinaDTO>

    @DELETE("${ApiRoutes.RUTINAS}/eliminar/{idRutina}")
    suspend fun eliminarRutina(
        @Path("idRutina") idRutina: String
    ): Response<Void>

    @GET("${ApiRoutes.RUTINAS}/buscarRutinas")
    suspend fun buscarRutinas(
        @Query("nombre") nombre: String? = null
    ): Response<List<RutinaBuscadorDto>>

    @GET("${ApiRoutes.RUTINAS}/autor/{creadorId}")
    suspend fun obtenerRutinasPorAutor(
        @Path("creadorId") creadorId: String
    ): Response<List<RutinaBuscadorDto>>

}