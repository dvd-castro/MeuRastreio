package br.com.davidcastro.meurastreio.features.details.mvi

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

sealed class DetailsAction {
    data class DeleteTracking(
        val code: String
    ): DetailsAction()

    data class ShareTracking(
        val trackingDomain: TrackingDomain
    ): DetailsAction()

    data class SaveTracking(
        val trackingDomain: TrackingDomain
    ): DetailsAction()

    data class ShowSetNameDialog(
        val enable: Boolean
    ): DetailsAction()
}