package com.example.rutifyclient.apiservice.network.api.comunidad.comentarios

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.comentario.ComentarioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiComentarios {

    @Multipart
    @POST("${ApiRoutes.COMENTARIOS}/comentarios")
    suspend fun crearComentario(
        @Part("comentario") comentario: RequestBody,
        @Part imagen: MultipartBody.Part?
    ): Response<ComentarioDto>

    @GET("${ApiRoutes.COMENTARIOS}/comentarios")
    suspend fun obtenerComentarios(): Response<List<ComentarioDto>>

    @GET("${ApiRoutes.COMENTARIOS}/comentarios/{id}/respuestas")
    suspend fun obtenerRespuestas(
        @Path("id") idComentario: String
    ): Response<List<ComentarioDto>>

    @POST("${ApiRoutes.COMENTARIOS}/comentarios/respuestas")
    suspend fun responderComentario(
        @Body respuestaDto: ComentarioDto
    ): Response<ComentarioDto>

    @PUT("${ApiRoutes.COMENTARIOS}/comentarios/aprobar")
    suspend fun aprobarComentario(
        @Body comentario: ComentarioDto
    ): Response<Unit>

    @DELETE("${ApiRoutes.COMENTARIOS}/comentarios/{idComentario}")
    suspend fun eliminarComentario(
        @Path("idComentario") idComentario: String
    ): Response<Unit>

    @GET("${ApiRoutes.COMENTARIOS}/autor/{creadorId}")
    suspend fun obtenerComentariosPorAutor(
        @Path("creadorId") creadorId: String
    ): Response<List<ComentarioDto>>

    @GET("${ApiRoutes.COMENTARIOS}/autorComentario/{nombre}")
    suspend fun obtenerComentariosPorNombre(
        @Path("nombre") nombre: String
    ): Response<List<ComentarioDto>>
}
