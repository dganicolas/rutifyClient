package com.example.rutifyclient.apiservice.network.api.reportes

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiReportes {
    @POST("v1/reportes/reportar/{idFirebase}")
    suspend fun reportarUsuario(@Path("idFirebase") idFirebase: String): Response<ResponseBody>
}
