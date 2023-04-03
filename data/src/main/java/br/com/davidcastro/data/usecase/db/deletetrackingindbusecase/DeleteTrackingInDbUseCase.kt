package br.com.davidcastro.data.usecase.db.deletetrackingindbusecase

interface DeleteTrackingInDbUseCase {
    suspend fun deleteTracking(code: String)
}