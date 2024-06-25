package br.com.davidcastro.meurastreio.features.home.mvi

import br.com.davidcastro.meurastreio.core.navigation.Routes


sealed class HomeAction {
    data object GetAllTracking: HomeAction()

    data object ReloadAllTracking: HomeAction()

    data class GetTracking(
        val code: String
    ): HomeAction()

    data class CheckIfHasAlreadyInserted(
        val code: String
    ): HomeAction()

    data class ShowError(
        val enabled: Boolean
    ): HomeAction()

    data class UpdateTrackingFilter(val filter: String
    ): HomeAction()

    data class NavigateTo(
        val route: Routes
    ): HomeAction()
}