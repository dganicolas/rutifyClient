package com.example.rutifyclient.apiservice.network.api.coinPack

import com.example.rutifyclient.domain.tienda.CoinPack
import retrofit2.Response
import retrofit2.http.GET

interface CoinPackApi {
    @GET("/v1/coin-packs")
    suspend fun obtenerPacks(): Response<List<CoinPack>>
}