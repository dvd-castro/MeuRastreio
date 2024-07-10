package br.com.davidcastro.meurastreio.commons.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.commons.utils.Utils.getTrackingStatusColor
import br.com.davidcastro.meurastreio.core.theme.GetCardBackgroundColor


@Composable
fun TrackingCard(
    modifier: Modifier = Modifier,
    name: String? = null,
    code: String? = null,
    status: String,
    local: List<String>,
    date: String,
    onItemClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = GetCardBackgroundColor()
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.dimen16dp)
        ) {

            name?.let {
                Text(
                    text = it,
                    fontSize = Dimens.size16sp,
                    fontWeight = FontWeight.Medium
                )
            }

            code?.let {
                SelectionContainer {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = Dimens.size16sp
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = Dimens.dimen4dp),
                text = status,
                fontSize = Dimens.size16sp,
                fontWeight = FontWeight.Medium,
                color = Color(
                    getTrackingStatusColor(
                        status = status
                    )
                )
            )

            local.forEach {
                Text(
                    text = it,
                    fontSize = Dimens.size16sp,
                    modifier = Modifier.padding(top = Dimens.dimen4dp)
                )
            }

            Text(
                text = date,
                fontSize = Dimens.size16sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = Dimens.dimen4dp)
            )
        }
    }
}