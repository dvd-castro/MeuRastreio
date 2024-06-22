package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import br.com.davidcastro.meurastreio.core.theme.GetCardBackgroundColor
import br.com.davidcastro.meurastreio.core.utils.Dimens
import br.com.davidcastro.meurastreio.core.utils.Utils.getTrackingStatusColor
import br.com.davidcastro.meurastreio.core.utils.extensions.IfNotNullOrEmpty
import br.com.davidcastro.meurastreio.core.utils.extensions.or
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

@Composable
fun TrackingCardList(
    list: List<TrackingDomain>,
    onItemClick: (TrackingDomain) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { tracking ->

            val lastEventStatus = tracking.getLastEvent()?.status.or {
                "Nenhuma atualização no momento"
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.dimen16dp)
                    .clickable {
                        onItemClick(tracking)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = GetCardBackgroundColor()
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.dimen16dp)
                ) {
                    tracking.name.IfNotNullOrEmpty {
                        Text(text = it)
                    }

                    SelectionContainer {
                        Text(
                            text = tracking.code,
                            fontWeight = FontWeight.Bold,
                            fontSize = Dimens.size16sp
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = Dimens.dimen4dp),
                        text = lastEventStatus,
                        fontSize = Dimens.size16sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(
                            getTrackingStatusColor(
                                context = context,
                                status = lastEventStatus
                            )
                        )
                    )

                    tracking.getLastEvent()?.local.IfNotNullOrEmpty {
                        Text(
                            text = it.toLowerCase(Locale.current).capitalize(Locale.current),
                            fontSize = Dimens.size16sp,
                            modifier = Modifier.padding(top = Dimens.dimen4dp)
                        )
                    }

                    tracking.getLastEvent()?.date.IfNotNullOrEmpty {
                        Text(
                            text = it,
                            fontSize = Dimens.size16sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = Dimens.dimen4dp)
                        )
                    }
                }
            }
        }
    }
}