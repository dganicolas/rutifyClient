import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasPatchDto
import com.example.rutifyclient.viewModel.ViewModelBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.LocalDate

class EstadisticasViewModel : ViewModelBase() {

    private val _esMes = MutableLiveData(false)
    val esMes: LiveData<Boolean> = _esMes

    private val _fechaSeleccionada = MutableLiveData(LocalDate.now())
    val fechaSeleccionada: LiveData<LocalDate> = _fechaSeleccionada

    private val _estadisticasDia = MutableLiveData<EstadisticasDiariasDto>(
        EstadisticasDiariasDto(
            null,
            "",
            LocalDate.now(),
            0.0,
            0.0,
            0,
            0.0
        )
    )
    val estadisticasDia: LiveData<EstadisticasDiariasDto> = _estadisticasDia


    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean> = _cargando

    private val _pesoInput = MutableLiveData("")
    val pesoInput: LiveData<String> = _pesoInput

    private val _mostrarDialogoPeso = MutableLiveData(false)
    val mostrarDialogoPeso: LiveData<Boolean> = _mostrarDialogoPeso

    fun abrirDialogoPeso() {
        _mostrarDialogoPeso.value = true
        _pesoInput.value = ""
    }

    fun cerrarDialogoPeso() {
        _mostrarDialogoPeso.value = false
    }

    fun setPesoInput(peso: String) {
        _pesoInput.value = peso
    }

    fun guardarPeso() {
        val peso = _pesoInput.value?.toDoubleOrNull() ?: 0.0
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.apiEstadisticasDiarias
                        .actualizarEstadisticasDiarias(
                            FirebaseAuth.getInstance().currentUser?.uid!!,
                            _fechaSeleccionada.value!!.toString(),
                            patch = EstadisticasDiariasPatchDto(
                                horasActivo =0.0,
                                ejerciciosRealizados =0,
                                kCaloriasQuemadas =0.0,
                                pesoCorporal = peso
                            )
                        )

                    if (response.isSuccessful) {
                        _estadisticasDia.value = response.body()
                    } else {
                        _estadisticasDia.value =
                            EstadisticasDiariasDto(null, "", LocalDate.now(), 0.0, 0.0, 0, 0.0)
                    }
                    obtenerEstadisticas()
                } catch (e: Exception) {
                    manejarErrorConexion(e)
                    _sinInternet.value = true
                } finally {
                    _cargando.value = false
                }
            cerrarDialogoPeso()
        }
    }

    fun cambiarFecha(fecha: LocalDate) {
        _fechaSeleccionada.value = fecha
        obtenerEstadisticas()
    }

    fun obtenerEstadisticas() {
        val fecha = _fechaSeleccionada.value
        _cargando.value = true
        _sinInternet.value = false

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiEstadisticasDiarias
                    .obtenerEstadisticasDiariasDia(
                        FirebaseAuth.getInstance().currentUser?.uid!!,
                        fecha!!
                    )

                if (response.isSuccessful) {
                    _estadisticasDia.value = response.body()
                } else {
                    _estadisticasDia.value =
                        EstadisticasDiariasDto(null, "", LocalDate.now(), 0.0, 0.0, 0, 0.0)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            } finally {
                _cargando.value = false
            }
        }
    }
}