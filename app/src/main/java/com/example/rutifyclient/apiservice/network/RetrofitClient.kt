package com.example.rutifyclient.apiservice.network
import com.example.rutifyclient.apiservice.network.api.usuarios.ApiUsuarios
import com.google.android.gms.common.api.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    //private const val BASE_URL = "https://app-adat-9a4d.onrender.com/"
    private const val BASE_URL = "http://192.168.1.43:8080/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)  // Tiempo de espera para la conexión
        .readTimeout(50, TimeUnit.SECONDS)     // Tiempo de espera para leer la respuesta
        .writeTimeout(50, TimeUnit.SECONDS)    // Tiempo de espera para escribir la solicitud
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiUsuarios: ApiUsuarios by lazy {
        retrofit.create(ApiUsuarios::class.java)
    }

//    val rutinaService: RutinaService by lazy {
//        retrofit.create(RutinaService::class.java)
//    }
}