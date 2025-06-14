package com.example.rutifyclient.apiservice.network

import com.example.rutifyclient.apiservice.network.api.coinPack.CoinPackApi
import com.example.rutifyclient.apiservice.network.api.compras.ApiCompras
import com.example.rutifyclient.apiservice.network.api.comunidad.comentarios.ApiComentarios
import com.example.rutifyclient.apiservice.network.api.cosmeticos.CosmeticoApi
import com.example.rutifyclient.apiservice.network.api.ejercicios.ApiEjercicios
import com.example.rutifyclient.apiservice.network.api.estadisticas.estadisticas.ApiEstadisticas
import com.example.rutifyclient.apiservice.network.api.estadisticas.estadisticasDiarias.ApiEstadisticasDiarias
import com.example.rutifyclient.apiservice.network.api.moderacion.ApiModeracion
import com.example.rutifyclient.apiservice.network.api.reportes.ApiReportes
import com.example.rutifyclient.apiservice.network.api.rutinas.ApiRutinas
import com.example.rutifyclient.apiservice.network.api.stripe.StripeApiService
import com.example.rutifyclient.apiservice.network.api.usuarios.ApiUsuarios
import com.example.rutifyclient.apiservice.network.api.votos.ApiVotos
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
            LocalDate.parse(json.asString)
        })
        .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
            JsonPrimitive(src.toString())
        })
        .serializeNulls()
        .setLenient()
        .setPrettyPrinting()
        .create()
    const val BASE_URL = "https://rutifyapi.onrender.com/"
    //const val BASE_URL = "http://192.168.1.43:8080/"
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

    val stripeApi: StripeApiService by lazy {
        retrofit.create(StripeApiService::class.java)
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

    val apiEstadisticasDiarias: ApiEstadisticasDiarias by lazy {
        retrofit.create(ApiEstadisticasDiarias::class.java)
    }

    val apiComentarios: ApiComentarios by lazy {
        retrofit.create(ApiComentarios::class.java)
    }

    val apiModeracion: ApiModeracion by lazy {
        retrofit.create(ApiModeracion::class.java)
    }

    val apiReportes: ApiReportes by lazy {
        retrofit.create(ApiReportes::class.java)
    }

    val apiCoinPack: CoinPackApi by lazy {
        retrofit.create(CoinPackApi::class.java)
    }

    val apiCosmeticos: CosmeticoApi by lazy {
        retrofit.create(CosmeticoApi::class.java)
    }

    val apiCompras: ApiCompras by lazy {
        retrofit.create(ApiCompras::class.java)
    }
}