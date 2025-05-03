package com.example.rutifyclient.apiservice.network

import com.example.rutifyclient.domain.UsuarioRegistroDTO
import com.example.rutifyclient.domain.UsuarioregistradoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("${ApiRoutes.USUARIOS}/registrarse")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegistroDTO): Response<UsuarioregistradoDto>
}