package com.example.rutifyclient.apiservice.network.api.usuarios

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.UsuarioCredencialesDto
import com.example.rutifyclient.domain.UsuarioInformacionDto
import com.example.rutifyclient.domain.UsuarioLoginDto
import com.example.rutifyclient.domain.UsuarioRegistroDTO
import com.example.rutifyclient.domain.UsuarioregistradoDto
import com.example.rutifyclient.domain.ActualizarUsuarioDTO
import com.example.rutifyclient.domain.EliminarUsuarioDTO
import com.example.rutifyclient.domain.UsuarioBusquedaDto
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
    suspend fun eliminarCuenta(@Body correo: EliminarUsuarioDTO, @Header("Authorization") token: String): Response<Void>

    @GET("${ApiRoutes.USUARIOS}/buscar/{nombre}")
    fun buscarUsuariosPorNombre(
        @Path("nombre") nombre: String,
    ): Response<List<UsuarioBusquedaDto>>  // UsuarioBusquedaDto es la clase de respuesta

    // Obtener detalle del usuario por idFirebase
    @GET("${ApiRoutes.USUARIOS}/detalle/{idFirebase}")
    fun obtenerDetalleUsuario(
        @Path("idFirebase") idFirebase: String,
        @Header("Authorization") token: String, // Suponiendo que la autenticación se pasa por un token
    ): Response<UsuarioInformacionDto>  // UsuarioInformacionDto es la clase de respuesta

    // Actualizar cuenta de usuario
    @PUT("${ApiRoutes.USUARIOS}/actualizar")
    fun actualizarCuenta(
        @Header("Authorization") token: String, // El token de autenticación
        @Body actualizarUsuarioDTO: ActualizarUsuarioDTO,
    ): Response<ActualizarUsuarioDTO> // ActualizarUsuarioDTO es la clase de datos a actualizar
}