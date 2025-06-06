import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.domain.tienda.CoinPack

@Composable
fun CoinPackCard(
    pack: CoinPack,
    onBuyClick: (CoinPack) -> Unit,
    modifier: Modifier,
) {
    TarjetaNormal(
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextoSubtitulo(R.string.texto_input, pack.nombre)
            Icono(
                imagen = painterResource(R.drawable.c1),
                modifier = Modifier.size(60.dp),
                descripcion = R.string.icono,
                onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            TextoSubtitulo(R.string.monedas, pack.monedas)
            TextoInformativo(R.string.precio, pack.precio)

            Spacer(modifier = Modifier.height(12.dp))
            ButtonPrincipal(
                R.string.comprar,
                {
                    onBuyClick(pack)
                },
                modifier = Modifier
            )
        }
    }
}