package br.com.davidcastro.data.usecase.remote.reloadalltrackingusecase

interface ReloadAllTrackingUseCase {
    suspend fun reload(): Boolean
}