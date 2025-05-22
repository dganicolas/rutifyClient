package com.example.rutifyclient.apiservice.network

import com.example.rutifyclient.apiservice.network.api.ejercicios.ApiEjercicios
import com.example.rutifyclient.apiservice.network.api.estadisticas.ApiEstadisticas
import com.example.rutifyclient.apiservice.network.api.rutinas.ApiRutinas
import com.example.rutifyclient.apiservice.network.api.usuarios.ApiUsuarios
import com.example.rutifyclient.apiservice.network.api.votos.ApiVotos
import com.google.android.gms.common.api.Api
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .setPrettyPrinting()
        .create()
    //private const val BASE_URL = "https://app-adat-9a4d.onrender.com/"
    private const val BASE_URL = "http://192.168.1.131:8080/"
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val token = FirebaseAuth.getInstance().currentUser?.let {
            try {
                Tasks.await(it.getIdToken(false)).token
            } catch (e: Exception) {
                null
            }
        }

        val requestBuilder = original.newBuilder()

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val request = requestBuilder.build()
        chain.proceed(request)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .connectTimeout(50, TimeUnit.SECONDS)  // Tiempo de espera para la conexión
        .readTimeout(50, TimeUnit.SECONDS)     // Tiempo de espera para leer la respuesta
        .writeTimeout(50, TimeUnit.SECONDS)    // Tiempo de espera para escribir la solicitud
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiUsuarios: ApiUsuarios by lazy {
        retrofit.create(ApiUsuarios::class.java)
    }
    val apiEjercicios: ApiEjercicios by lazy {
        retrofit.create(ApiEjercicios::class.java)
    }

    val apiRutinas: ApiRutinas by lazy {
        retrofit.create(ApiRutinas::class.java)
    }

    val apiEstadisticas: ApiEstadisticas by lazy {
        retrofit.create(ApiEstadisticas::class.java)
    }

    val apiVotos: ApiVotos by lazy {
        retrofit.create(ApiVotos::class.java)
    }
}