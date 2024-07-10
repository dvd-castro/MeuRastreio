package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.davidcastro.meurastreio.commons.components.TrackingCard
import br.com.davidcastro.meurastreio.commons.utils.extensions.or
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

@Composable
fun HomeTrackingCardList(
    list: List<TrackingDomain>,
    onItemClick: (TrackingDomain) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { tracking ->

            val lastEventStatus = tracking.getLastEvent()?.status.or {
                "Nenhuma atualização no momento"
            }

            TrackingCard(
                name = tracking.name,
                code = tracking.code,
                status = lastEventStatus,
                local = tracking.getLastEvent()?.subStatus.orEmpty(),
                date = tracking.getLastEvent()?.date.orEmpty()
            ) {
                onItemClick(tracking)
            }
        }
    }
}