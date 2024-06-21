package br.com.davidcastro.meurastreio.core.navigation

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data class DetailScreen(
        val tracking: TrackingDomain,
        val isFromResult: Boolean = false
    ): Routes()
}