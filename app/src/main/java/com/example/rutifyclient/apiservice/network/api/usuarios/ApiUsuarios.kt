package com.example.rutifyclient.apiservice.network.api.usuarios

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.usuario.UsuarioCredencialesDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.domain.usuario.UsuarioLoginDto
import com.example.rutifyclient.domain.usuario.UsuarioRegistroDTO
import com.example.rutifyclient.domain.usuario.UsuarioregistradoDto
import com.example.rutifyclient.domain.usuario.ActualizarUsuarioDTO
import com.example.rutifyclient.domain.usuario.EliminarUsuarioDTO
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiUsuarios {
    @POST("${ApiRoutes.USUARIOS}/registrarse")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegistroDTO): Response<UsuarioregistradoDto>

    @POST("v1/usuarios/acceder")
    suspend fun loginUsuario(@Body usuario: UsuarioCredencialesDto): Response<UsuarioLoginDto>

    @DELETE("${ApiRoutes.USUARIOS}/eliminar")
    suspend fun eliminarCuenta(@Body correo: EliminarUsuarioDTO): Response<Void>

    @GET("${ApiRoutes.USUARIOS}/buscar/{nombre}")
    suspend fun buscarUsuariosPorNombre(
        @Path("nombre") nombre: String,
    ): Response<List<UsuarioBusquedaDto>>

    // Obtener detalle del usuario por idFirebase
    @GET("${ApiRoutes.USUARIOS}/detalle/{idFirebase}")
    suspend fun obtenerDetalleUsuario(
        @Path("idFirebase") idFirebase: String,
    ): Response<UsuarioInformacionDto>

    // Actualizar cuenta de usuario
    @PUT("${ApiRoutes.USUARIOS}/actualizar")
    suspend fun actualizarCuenta(
        @Body actualizarUsuarioDTO: ActualizarUsuarioDTO,
    ): Response<ActualizarUsuarioDTO> // ActualizarUsuarioDTO es la clase de datos a actualizar

    @GET("${ApiRoutes.USUARIOS}/esAdmin/{idFirebase}")
    suspend fun esAdmin(
        @Path("idFirebase") idFirebase: String
    ): Response<Boolean>
}