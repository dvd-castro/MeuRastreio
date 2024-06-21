package br.com.davidcastro.meurastreio.features.home.mvi

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

sealed class HomeResult {
    data class OpenDetailScreen(
        val tracking: TrackingDomain,
        val isFromResult: Boolean = false
    ): HomeResult()
}