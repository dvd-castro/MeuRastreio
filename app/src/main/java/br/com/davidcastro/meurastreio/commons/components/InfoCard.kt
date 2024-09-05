package br.com.davidcastro.meurastreio.commons.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.core.theme.GetCardBackgroundColor
import br.com.davidcastro.meurastreio.core.theme.GetFontColor

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    message: String,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = GetCardBackgroundColor()
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            fontSize = Dimens.size14sp,
            color = GetFontColor(),
            modifier = Modifier.padding(Dimens.dimen16dp)
        )
    }
}