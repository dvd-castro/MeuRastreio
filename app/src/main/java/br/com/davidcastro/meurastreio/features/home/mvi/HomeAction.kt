package br.com.davidcastro.meurastreio.features.home.mvi

sealed class HomeAction {
    data class GetTracking(val code: String): HomeAction()
    data class ShowError(val enabled: Boolean): HomeAction()
    data class UpdateTrackingFilter(val filter: String): HomeAction()
    data object GetAllTracking: HomeAction()
    data object ReloadAllTracking: HomeAction()
}