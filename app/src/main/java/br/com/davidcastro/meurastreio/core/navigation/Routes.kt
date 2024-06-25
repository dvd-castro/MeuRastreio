package br.com.davidcastro.meurastreio.core.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data class DetailScreen(
        val tracking: String,
        val isFromResult: Boolean = false
    ): Routes()
}