package br.com.davidcastro.meurastreio.features.details.mvi

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

sealed class DetailsResult {
    data object ExitScreen: DetailsResult()
    data class ShareTracking(
        val trackingDomain: TrackingDomain
    ): DetailsResult()
}