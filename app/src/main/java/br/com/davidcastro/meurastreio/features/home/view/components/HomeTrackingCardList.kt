package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.davidcastro.meurastreio.commons.components.TrackingCard
import br.com.davidcastro.meurastreio.commons.utils.Dimens.dimen16dp
import br.com.davidcastro.meurastreio.commons.utils.extensions.or
import br.com.davidcastro.meurastreio.commons.utils.extensions.orFalse
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

@Composable
fun HomeTrackingCardList(
    list: List<TrackingDomain>,
    onItemClick: (TrackingDomain) -> Unit
) {
    if(list.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Nenhum rastreio adicionado!")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) { tracking ->

                val lastEventStatus = tracking.getLastEvent()?.status.or {
                    "Nenhuma atualização no momento"
                }

                TrackingCard(
                    modifier = Modifier.padding(bottom = dimen16dp),
                    name = tracking.name,
                    code = tracking.code,
                    status = lastEventStatus,
                    hasUpdate = tracking.hasUpdated.orFalse(),
                    date = tracking.getLastEvent()?.date.orEmpty(),
                    local = if(tracking.getLastEvent()?.subStatus?.isEmpty() == true)
                        listOf(tracking.getLastEvent()?.local.orEmpty())
                    else
                        tracking.getLastEvent()?.subStatus.orEmpty()
                ) {
                    onItemClick(tracking)
                }
            }
        }
    }
}