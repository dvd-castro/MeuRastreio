package br.com.davidcastro.meurastreio.domain.usecase.db.deletetrackingindbusecase

import br.com.davidcastro.meurastreio.data.repository.TrackingRepository

class DeleteTrackingInDbUseCaseImpl(
    private val trackingRepository: TrackingRepository
    ): DeleteTrackingInDbUseCase {

    override suspend fun deleteTracking(code: String) {
        trackingRepository.delete(code = code)
    }
}