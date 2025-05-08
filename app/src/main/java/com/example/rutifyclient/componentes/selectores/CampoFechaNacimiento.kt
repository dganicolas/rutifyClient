package com.example.rutifyclient.componentes.selectores

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.camposDeTextos.CampoTexto
import java.util.Calendar

@Composable
fun SelectorFechaNacimiento(
    fechaNacimiento: String,
    onFechaSeleccionada: (String) -> Unit,
    onFechaInvalida: () -> Unit,
) {
    val context = LocalContext.current
    val calendario = Calendar.getInstance()

    // Configuración del DatePicker
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val fechaSeleccionada = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            // Validación: No puede ser una fecha futura y debe ser mayor de 16 años
            val hoy = Calendar.getInstance()
            val edadMinima = Calendar.getInstance().apply {
                add(Calendar.YEAR, -16) // mínimo 16 años
            }

            if (fechaSeleccionada.after(hoy)) {
                onFechaInvalida() // Alerta si seleccionan una fecha futura
            } else if (fechaSeleccionada.after(edadMinima)) {
                onFechaInvalida() // Alerta si no tiene al menos 16 años
            } else {
                onFechaSeleccionada(
                    "%02d/%02d/%04d".format(
                        dayOfMonth,
                        month + 1,
                        year
                    )
                ) // Formato de la fecha
            }
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    // Componente de campo de texto, que abre el calendario al hacer clic
    CampoTexto(
        value = fechaNacimiento,
        onValueChange = {},
        textoIdLabel = R.string.fecha_nacimiento,
        modifierIcon = Modifier
            .clickable { datePickerDialog.show() },
        icono = Icons.Default.DateRange,
        descripcion = "Seleccionar fecha",
        readOnly = true
    )
}