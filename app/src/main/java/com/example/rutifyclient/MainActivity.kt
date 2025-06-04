package com.example.rutifyclient

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import com.example.rutifyclient.navigation.AppNavigation
import com.example.rutifyclient.ui.theme.RutifyClientTheme
import com.example.rutifyclient.viewModel.ajustes.SettingsViewModel
import com.stripe.android.PaymentConfiguration

val Context.dataStore by preferencesDataStore(name = "rutify_prefs")
class MainActivity : ComponentActivity() {
    private lateinit var settingsViewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51RVWnFQv2Fe7FJqoN9no90jKYuqtegh9WyOXCVWY3xPFORwFHoI2Ji8RgWNW6wAi8dlaIbnAnKCLmiKBiSgQ0xah00s4KK5SW6"
        )
        enableEdgeToEdge()
        settingsViewModel = SettingsViewModel(application)
        setContent {
            RutifyClientTheme(settingsViewModel) {
                AppNavigation(settingsViewModel)
            }
        }
    }
}
