package br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase

interface ReloadAllTrackingUseCase {
    suspend fun reload(): Boolean
}