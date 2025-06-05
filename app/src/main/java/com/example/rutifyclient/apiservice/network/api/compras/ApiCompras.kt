package com.example.rutifyclient.apiservice.network.api.compras

import com.example.rutifyclient.domain.tienda.compras.Compra
import com.example.rutifyclient.domain.tienda.cosmeticos.Cosmetico
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiCompras {

    @GET("/v1/compras/{idUsuario}")
    suspend fun obtenerComprasUsuario(
        @Path("idUsuario") idUsuario: String
    ): Response<List<Cosmetico>>

    @POST("/v1/compras")
    suspend fun registrarCompra(
        @Body compra: Compra
    ): Response<ResponseBody>
}
