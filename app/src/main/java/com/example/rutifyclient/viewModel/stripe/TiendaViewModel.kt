package com.example.rutifyclient.viewModel.stripe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.stripe.PaymentRequestDto
import com.example.rutifyclient.domain.tienda.CoinPack
import com.example.rutifyclient.domain.tienda.cosmeticos.Cosmetico
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TiendaViewModel : ViewModelBase() {

    private val _clientSecret = MutableLiveData<String?>(null)
    val clientSecret: LiveData<String?> = _clientSecret

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _packSeleccionado = MutableLiveData<CoinPack?>(null)
    private val _cosmeticoSeleccionado = MutableLiveData<Cosmetico?>(null)

    private val _showPaymentSheet = MutableLiveData(false)
    val showPaymentSheet: LiveData<Boolean> = _showPaymentSheet

    private val _packs = MutableLiveData(emptyList<CoinPack>())
    val packs: LiveData<List<CoinPack>> = _packs

    private val _cosmeticosPorTipo = MutableLiveData<Map<String, List<Cosmetico>>>()
    val cosmeticosPorTipo: LiveData<Map<String, List<Cosmetico>>> = _cosmeticosPorTipo

    private val _ventanaComprarComestico = MutableLiveData<Boolean>(false)
    val ventanaComprarComestico: LiveData<Boolean> = _ventanaComprarComestico

    fun fetchPaymentIntent(coins: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.stripeApi.createPaymentIntent(PaymentRequestDto(userId = _idFirebase.value!!,coins = coins))
                _clientSecret.value = response.body()?.clientSecret
            } catch (e: IOException) {
                mostrarToastApi("Error de red: ${e.localizedMessage}")
            } catch (e: HttpException) {
                mostrarToastApi("Error HTTP: ${e.code()} - ${e.message()}")
            } catch (e: Exception) {
                mostrarToastApi("Error: ${e.localizedMessage}")
            }
        }
    }

    fun selectPackAndCreatePaymentIntent(pack: CoinPack) {
        _packSeleccionado.value = pack
        fetchPaymentIntent(pack.monedas)
    }

    fun triggerPaymentSheet() {
        _showPaymentSheet.value = true
    }

    fun paymentSheetDisplayed() {
        _showPaymentSheet.value = false
    }

    fun clearError() {
        _error.value = null
    }

    fun obtenerCosmeticosPorTipo() {
        _estado.value = false
        viewModelScope.launch {
            try {
                val cosmeticos = RetrofitClient.apiCosmeticos.obtenerTodos()
                val agrupados = cosmeticos.groupBy { it.tipo }
                _cosmeticosPorTipo.value = agrupados
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            } finally {
                _estado.value = true
            }
        }
    }

    fun obtenerPacks(){
        _estado.value = false
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiCoinPack
                    .obtenerPacks()

                if (response.isSuccessful) {
                    _packs.value = response.body()
                } else {
                   mostrarToastApi(response.errorBody()?.string()?:"")
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            } finally {
                _estado.value = true
            }
        }
    }

    fun PopUpComprarCosmeticos() {
        _ventanaComprarComestico.value = !_ventanaComprarComestico.value!!
    }

    fun guardarComesticos(cosmetico: Cosmetico) {
        _cosmeticoSeleccionado.value = cosmetico
    }

    fun comprarCosmetico() {
        TODO("Not yet implemented")
    }
}
