package com.example.rutifyclient.componentes.tarjetas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.rutifyclient.R
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun TarjetaAnuncio(context: Context, modifier: Modifier = Modifier) {
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

    // Cargar el anuncio nativo una sola vez
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                nativeAd?.destroy()
                nativeAd = ad
            }
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            // Inflar layout nativo de anuncio
            val adView = LayoutInflater.from(ctx)
                .inflate(R.xml.native_ad_layout, null) as NativeAdView

            // Asignar los componentes del anuncio al NativeAdView
            adView.headlineView = adView.findViewById(R.id.ad_headline)
            adView.bodyView = adView.findViewById(R.id.ad_body)
            adView.iconView = adView.findViewById(R.id.ad_icon)
            adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)

            adView
        },
        update = { adView ->
            val ad = nativeAd
            if (ad != null) {
                val headline = ad.headline
                (adView.headlineView as TextView).text = headline

                val body = ad.body
                val bodyView = adView.bodyView as TextView
                if (body != null) {
                    bodyView.text = body
                    bodyView.visibility = View.VISIBLE
                } else {
                    bodyView.visibility = View.GONE
                }

                val icon = ad.icon
                val iconView = adView.iconView as ImageView
                if (icon != null) {
                    iconView.setImageDrawable(icon.drawable)
                    iconView.visibility = View.VISIBLE
                } else {
                    iconView.visibility = View.GONE
                }

                val callToAction = ad.callToAction
                val ctaView = adView.callToActionView as Button
                if (callToAction != null) {
                    ctaView.text = callToAction
                    ctaView.visibility = View.VISIBLE
                } else {
                    ctaView.visibility = View.GONE
                }

                adView.setNativeAd(ad)
            }
        }
    )
}