package br.com.davidcastro.data.usecase

interface DeleteTrackingUseCase {
    suspend fun deleteTracking(code: String)
}