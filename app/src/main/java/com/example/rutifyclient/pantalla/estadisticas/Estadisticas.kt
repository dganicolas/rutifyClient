package com.example.rutifyclient.pantalla.estadisticas

import EstadisticasViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.selectores.SelectorFechaNacimiento
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasDto
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.ViewModelBase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Estadisticas(navControlador: NavHostController) {
    val viewModel: EstadisticasViewModel = viewModel()
    val fecha by viewModel.fechaSeleccionada.observeAsState(LocalDate.now())
    val estadisticaDia by viewModel.estadisticasDia.observeAsState(
        EstadisticasDiariasDto(null, "", LocalDate.now(), 0.0, 0.0, 0, 0.0)
    )
    val mostrarDialogoPeso by viewModel.mostrarDialogoPeso.observeAsState(false)
    val estado by viewModel.estado.observeAsState(false)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val pesoInput by viewModel.pesoInput.observeAsState("")
    LaunchedEffect(Unit) {
        viewModel.cambiarFecha(fecha)
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.reiniciarInternet()
            viewModel.cambiarFecha(fecha)
        },
        topBar = ({
            TopBarBase(R.string.estadisticas, ({
                Icono(
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    descripcion = R.string.volver,
                    onClick = { navControlador.popBackStack() },
                    tint = colorScheme.onBackground
                )
            }))
        }),
        iconoFloatingButton = Icons.Default.MonitorWeight,
        onClickFloatingButton = { viewModel.abrirDialogoPeso() }
    ) {
        Box(modifier = Modifier.padding(it)) {
            TarjetaNormal {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icono(
                            icono = Icons.AutoMirrored.Filled.ArrowBack,
                            descripcion = R.string.icono,
                            onClick = { viewModel.cambiarFecha(fecha.minusDays(1)) })
                        Spacer(modifier = Modifier.width(16.dp))
                        TextoSubtitulo(R.string.texto_input, fecha.toString())
                        Spacer(modifier = Modifier.width(16.dp))
                        Icono(
                            icono = Icons.AutoMirrored.Filled.ArrowForward,
                            descripcion = R.string.icono,
                            onClick = { viewModel.cambiarFecha(fecha.plusDays(1)) })
                    }
                    Column {
                        if(!estado) return@Column
                        if (estadisticaDia._id == null) {
                            TextoTitulo(R.string.noExistenMediciones)
                        } else {
                            // Tarjeta para ejercicios realizados
                            TarjetaNormal(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icono(
                                        descripcion = R.string.icono,
                                        icono = Icons.Default.FitnessCenter,
                                        onClick = {})
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextoTitulo(
                                        R.string.ejerciciosRealizados,
                                        estadisticaDia.ejerciciosRealizados
                                    )
                                }
                            }

                            // Tarjeta para calorías estimadas
                            TarjetaNormal(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icono(
                                        descripcion = R.string.icono,
                                        icono = Icons.Default.LocalFireDepartment,
                                        onClick = {})
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextoTitulo(
                                        R.string.caloriasEstimadas,
                                        estadisticaDia.kCaloriasQuemadas
                                    )
                                }
                            }

                            // Tarjeta para horas activo
                            TarjetaNormal(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icono(
                                        descripcion = R.string.icono,
                                        icono = Icons.Default.AccessTime,
                                        onClick = {})
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextoTitulo(R.string.horasActivoEst, estadisticaDia.horasActivo)
                                }
                            }

                            // Tarjeta para peso corporal
                            TarjetaNormal(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icono(
                                        descripcion = R.string.icono,
                                        icono = Icons.Default.MonitorWeight,
                                        onClick = {})
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextoTitulo(R.string.pesoUsuario, estadisticaDia.pesoCorporal)
                                }
                            }
                        }
                    }
                }
            }
        }
        if (mostrarDialogoPeso) {
            AlertDialog(
                onDismissRequest = { viewModel.cerrarDialogoPeso() },
                title = { Text(text = "Agregar peso") },
                text = {
                    Column {
                        Text(text = "Fecha: ${fecha.format(DateTimeFormatter.ISO_DATE)}")

                        Spacer(modifier = Modifier.height(8.dp))

                        androidx.compose.material3.OutlinedTextField(
                            value = pesoInput,
                            onValueChange = { nuevoValor ->
                                if (nuevoValor.matches(Regex("^\\d*\\.?\\d*\$"))) {
                                    viewModel.setPesoInput(nuevoValor)
                                }
                            },
                            label = { Text("Peso (kg)") },
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    androidx.compose.material3.TextButton(
                        onClick = { viewModel.guardarPeso() }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    androidx.compose.material3.TextButton(
                        onClick = { viewModel.cerrarDialogoPeso() }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
