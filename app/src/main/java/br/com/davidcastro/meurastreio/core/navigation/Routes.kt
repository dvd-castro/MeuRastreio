package br.com.davidcastro.meurastreio.core.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home: Routes()
}