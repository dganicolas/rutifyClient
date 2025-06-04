package com.example.rutifyclient.apiservice.network.api.cosmeticos

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.tienda.cosmeticos.Cosmetico
import retrofit2.http.GET

interface CosmeticoApi {
    @GET(ApiRoutes.COSMETICOS)
    suspend fun obtenerTodos(): List<Cosmetico>
}