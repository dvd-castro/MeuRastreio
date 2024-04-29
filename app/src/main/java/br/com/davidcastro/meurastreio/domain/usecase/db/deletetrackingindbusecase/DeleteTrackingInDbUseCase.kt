package br.com.davidcastro.meurastreio.domain.usecase.db.deletetrackingindbusecase

interface DeleteTrackingInDbUseCase {
    suspend fun deleteTracking(code: String)
}