package br.com.davidcastro.data.usecase.db

interface DeleteTrackingInDbUseCase {
    suspend fun deleteTracking(code: String)
}