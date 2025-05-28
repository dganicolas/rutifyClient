package com.example.rutifyclient.apiservice.network.api.comunidad

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.comentario.ComentarioDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiComunidad {

    @Multipart
    @POST("${ApiRoutes.COMUNIDAD}/comentarios")
    suspend fun crearComentario(
        @Part("comentario") comentario: RequestBody,
        @Part imagen: MultipartBody.Part?
    ): Response<ComentarioDto>

    @GET("${ApiRoutes.COMUNIDAD}/comentarios")
    suspend fun obtenerComentarios(): Response<List<ComentarioDto>>

    @GET("${ApiRoutes.COMUNIDAD}/comentarios/{id}/respuestas")
    suspend fun obtenerRespuestas(
        @Path("id") idComentario: String
    ): Response<List<ComentarioDto>>

    @POST("${ApiRoutes.COMUNIDAD}/comentarios/{id}/respuestas")
    suspend fun responderComentario(
        @Path("id") idComentario: String,
        @Body respuestaDto: ComentarioDto
    ): Response<ComentarioDto>
}
