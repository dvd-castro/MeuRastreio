package br.com.davidcastro.meurastreio.commons.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.commons.utils.Utils.getTrackingStatusColor
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
            containerColor = GetPrimaryColor()
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
                        text = it.capitalize(Locale.current),
                        fontSize = Dimens.size16sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    if(hasUpdate) {
                        Text(
                            modifier = Modifier
                                .clip(AbsoluteRoundedCornerShape(Dimens.dimen8dp))
                                .background(Red)
                                .padding(horizontal = Dimens.dimen4dp),
                            fontSize = Dimens.size12sp,
                            text = stringResource(R.string.state_updated),
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
                modifier = Modifier.padding(top = Dimens.dimen2dp),
                text = status,
                fontSize = Dimens.size16sp,
                fontWeight = FontWeight.Bold,
                color = Color(
                    getTrackingStatusColor(
                        status = status
                    )
                )
            )

            local.forEach {
                GetSubStatusString(it)
            }

            Text(
                text = date,
                fontSize = Dimens.size16sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = Dimens.dimen2dp)
            )
        }
    }
}

@Composable
private fun GetSubStatusString(status: String) {
    if (
        status.contains(
            stringResource(R.string.message_acessar_importacoes_contains),
            ignoreCase = true
        )
    ) {
        val context = LocalContext.current
        val link = stringResource(id = R.string.message_importacoes_link)

        Text(
            text = stringResource(id = R.string.message_acessar_importacoes),
            fontSize = Dimens.size16sp,
            color = Color.Blue,
            modifier = Modifier
                .padding(top = Dimens.dimen2dp)
                .clickable {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link)
                        )
                    )
                }
        )
    } else {
        Text(
            text = status,
            fontSize = Dimens.size16sp,
            modifier = Modifier.padding(top = Dimens.dimen2dp)
        )
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