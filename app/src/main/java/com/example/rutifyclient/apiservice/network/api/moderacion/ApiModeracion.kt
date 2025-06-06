package com.example.rutifyclient.apiservice.network.api.moderacion

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiModeracion {

    @GET("${ApiRoutes.MODERACION}/verificar")
    suspend fun cargarComentariosNoVerificados(): Response<List<ComentarioDto>>

    @DELETE("${ApiRoutes.MODERACION}/eliminar/{id}")
    suspend fun eliminarComentario( @Path("id") id: String): Response<Unit>

    @GET("${ApiRoutes.MODERACION}/reportados")
    suspend fun obtenerUsuariosReportados(
    ): Response<List<UsuarioBusquedaDto>>

    @DELETE("${ApiRoutes.MODERACION}/usuario/{id}")
    suspend fun obtenerUsuariosReportados( @Path("id") id: String): Response<Unit>
}