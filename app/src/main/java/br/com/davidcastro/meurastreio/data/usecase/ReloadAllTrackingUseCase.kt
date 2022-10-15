package br.com.davidcastro.meurastreio.data.usecase

interface ReloadAllTrackingUseCase {
    suspend fun reload(): Boolean
}