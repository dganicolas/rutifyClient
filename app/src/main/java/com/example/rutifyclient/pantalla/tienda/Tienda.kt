package com.example.rutifyclient.pantalla.tienda

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmar
import com.example.rutifyclient.componentes.dialogoDeAlerta.AlertDialogConfirmarCompra
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.componentes.ventanas.CoinPackGrid
import com.example.rutifyclient.componentes.ventanas.MostrarCosmeticosLazyRows
import com.example.rutifyclient.domain.tienda.CoinPack
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.stripe.TiendaViewModel
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet

@Composable
fun Tienda(navControlador: NavHostController) {
    val viewModel: TiendaViewModel = viewModel()
    val showPaymentSheet by viewModel.showPaymentSheet.observeAsState(false)
    val estado by viewModel.estado.observeAsState(false)
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val packs by viewModel.packs.observeAsState(emptyList<CoinPack>())
    val clientSecret by viewModel.clientSecret.observeAsState()
    val cosmeticosPorTipo by viewModel.cosmeticosPorTipo.observeAsState(emptyMap())
    val ventanaComprarComestico by viewModel.ventanaComprarComestico.observeAsState(false)

    val paymentSheet = rememberPaymentSheet { result ->
        when (result) {
            is PaymentSheetResult.Completed -> viewModel.mostrarToast(R.string.pagoCompletado)
            is PaymentSheetResult.Canceled -> viewModel.mostrarToast(R.string.pagoCancelado)
            is PaymentSheetResult.Failed -> {
                viewModel.clearError()
                viewModel.mostrarToastApi("Error: ${result.error.localizedMessage}")
            }
        }
        viewModel.paymentSheetDisplayed()
    }
    LaunchedEffect(Unit) {
        viewModel.obtenerPacks()
        viewModel.obtenerCosmeticosPorTipo()
    }

    LaunchedEffect(showPaymentSheet, clientSecret) {
        if (showPaymentSheet && clientSecret != null) {
            val config = PaymentSheet.Configuration("Rutify")
            paymentSheet.presentWithPaymentIntent(clientSecret!!, config)
        }
    }

    if (ventanaComprarComestico) {
        AlertDialogConfirmarCompra(
            R.string.seguroComprar,
            R.string.accionIrreversible,
            {
                viewModel.PopUpComprarCosmeticos()
                viewModel.comprarCosmetico()
            },
            {
                viewModel.PopUpComprarCosmeticos()
            },
            R.string.comprar
        )
    }

    PantallaBase(
        navControlador,
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerUsuario()
        },
        topBar = ({ TopBarBase(R.string.tienda) }),
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.Tienda) })
    ) {
        TarjetaNormal(
            modifierTarjeta = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    MostrarCosmeticosLazyRows(
                        cosmeticosPorTipo,
                        { cosmetico ->
                            viewModel.PopUpComprarCosmeticos()
                            viewModel.guardarComesticos(cosmetico)
                        }
                    )

                    TextoTitulo(R.string.comprarMonedas)
                    CoinPackGrid(
                        packs = packs,
                        onBuyClick = {
                            viewModel.selectPackAndCreatePaymentIntent(it)
                            viewModel.triggerPaymentSheet()
                        }
                    )
                }
            }
        }
    }
}







