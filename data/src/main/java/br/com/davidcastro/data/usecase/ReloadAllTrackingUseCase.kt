package br.com.davidcastro.data.usecase

interface ReloadAllTrackingUseCase {
    suspend fun reload(): Boolean
}