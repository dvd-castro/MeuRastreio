package br.com.davidcastro.meurastreio.features.details.mvi

data class DetailsState(
    val operationCompleted: Boolean = false,
    val showSetNameDialog: Boolean = false,
)