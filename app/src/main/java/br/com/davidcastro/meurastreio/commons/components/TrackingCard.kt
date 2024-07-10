package br.com.davidcastro.meurastreio.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.commons.utils.Utils.getTrackingStatusColor
import br.com.davidcastro.meurastreio.core.theme.GetCardBackgroundColor
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.core.theme.Red


@Composable
fun TrackingCard(
    modifier: Modifier = Modifier,
    name: String? = null,
    code: String? = null,
    status: String,
    local: List<String>,
    date: String,
    hasUpdate: Boolean = false,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it,
                        fontSize = Dimens.size16sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    if(hasUpdate) {
                        Text(
                            modifier = Modifier
                                .clip(AbsoluteRoundedCornerShape(Dimens.dimen8dp))
                                .background(Red)
                                .padding(Dimens.dimen4dp),
                            text = "Atualizado",
                            color = GetPrimaryColor()
                        )
                    }
                }
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

@Preview
@Composable
private fun TrackingCardPreview() {
    TrackingCard(
        name = "David",
        status = "Objeto entregue ao destinatário",
        local = listOf("São Paulo"),
        date = "26/11/1999",
        hasUpdate = true
    )
}