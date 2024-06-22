package br.com.davidcastro.meurastreio.features.home.mvi

import br.com.davidcastro.meurastreio.core.navigation.Routes

sealed class HomeResult {
    data class NavigateTo(val route: Routes): HomeResult()
}