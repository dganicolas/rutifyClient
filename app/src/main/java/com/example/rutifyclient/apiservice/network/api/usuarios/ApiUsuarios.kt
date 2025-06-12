package com.example.rutifyclient.apiservice.network.api.usuarios

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.tienda.cosmeticos.Cosmetico
import com.example.rutifyclient.domain.usuario.ActualizarUsuarioDTO
import com.example.rutifyclient.domain.usuario.EliminarUsuarioDTO
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import com.example.rutifyclient.domain.usuario.UsuarioCredencialesDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.domain.usuario.UsuarioLoginDto
import com.example.rutifyclient.domain.usuario.UsuarioRegistroDTO
import com.example.rutifyclient.domain.usuario.UsuarioregistradoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiUsuarios {
    @POST("${ApiRoutes.USUARIOS}/registrarse")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegistroDTO): Response<UsuarioregistradoDto>

    @POST("${ApiRoutes.USUARIOS}/acceder")
    suspend fun loginUsuario(@Body usuario: UsuarioCredencialesDto): Response<UsuarioLoginDto>

    @HTTP(method = "DELETE", path = "${ApiRoutes.USUARIOS}/eliminar", hasBody = true)
    suspend fun eliminarCuenta(@Body correo: EliminarUsuarioDTO): Response<Unit>

    @GET("${ApiRoutes.USUARIOS}/buscar/{nombre}/{pagina}/{tamano}")
    suspend fun buscarUsuariosPorNombre(
        @Path("nombre") nombre: String,
        @Path("pagina") pagina: Int,
        @Path("tamano") tamano: Int
    ): Response<List<UsuarioBusquedaDto>>

    @GET("${ApiRoutes.USUARIOS}/detalle/{idFirebase}")
    suspend fun obtenerDetalleUsuario(
        @Path("idFirebase") idFirebase: String,
    ): Response<UsuarioInformacionDto>

    @PUT("${ApiRoutes.USUARIOS}/actualizar")
    suspend fun actualizarCuenta(
        @Body actualizarUsuarioDTO: ActualizarUsuarioDTO,
    ): Response<ActualizarUsuarioDTO>

    @GET("${ApiRoutes.USUARIOS}/esAdmin/{idFirebase}")
    suspend fun esAdmin(
        @Path("idFirebase") idFirebase: String
    ): Response<Boolean>

    @POST("${ApiRoutes.USUARIOS}/reto-diario")
    suspend fun marcarRetoDiario(
    ): Response<Boolean>

    @PUT("${ApiRoutes.USUARIOS}/avatar/cosmetico")
    suspend fun aplicarCosmetico(
        @Body cosmetico: Cosmetico
    ): Response<Unit>
}