package br.com.davidcastro.meurastreio.features.details.mvi

sealed class DetailsResult {
    data object ExitScreen: DetailsResult()
}