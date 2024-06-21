package br.com.davidcastro.meurastreio.features.home.mvi

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

data class HomeState (
    val hasNotEvents: Boolean = false,
    val hasError: Boolean = false,
    val hasLoading: Boolean = false,
    val hasAlreadyInserted: Boolean = false,
    val allTrackings: List<TrackingDomain> = emptyList(),
    val currentSelectedFilter: List<TrackingDomain> = emptyList()
)