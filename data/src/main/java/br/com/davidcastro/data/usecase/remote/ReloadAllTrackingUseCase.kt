package br.com.davidcastro.data.usecase.remote

interface ReloadAllTrackingUseCase {
    suspend fun reload(): Boolean
}