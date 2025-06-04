package com.example.rutifyclient.componentes.ventanas

import CoinPackCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.domain.tienda.CoinPack

@Composable
fun CoinPackGrid(
    packs: List<CoinPack>,
    onBuyClick: (CoinPack) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(packs) { pack ->
            CoinPackCard(
                pack = pack,
                onBuyClick = onBuyClick,
                modifier = Modifier
            )
        }
    }
}