package br.com.davidcastro.meurastreio.domain.usecase.db.deletetrackingindbusecase

interface DeleteTrackingInDbUseCase {
    suspend operator fun invoke(code: String)
}