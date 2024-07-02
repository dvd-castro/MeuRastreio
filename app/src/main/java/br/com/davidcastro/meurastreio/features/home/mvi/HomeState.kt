package br.com.davidcastro.meurastreio.features.home.mvi

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

data class HomeState (
    val hasError: ErrorType = ErrorType.NONE,
    val hasLoading: Boolean = false,
    val allTrackings: List<TrackingDomain> = emptyList(),
    val currentSelectedFilter: List<TrackingDomain> = emptyList()
)

sealed class ErrorType {
    data object ErrorGetInDb: ErrorType()
    data object ErrorGetInRemote: ErrorType()
    data object ErrorHasAlreadyInserted: ErrorType()
    data object NONE: ErrorType()
}