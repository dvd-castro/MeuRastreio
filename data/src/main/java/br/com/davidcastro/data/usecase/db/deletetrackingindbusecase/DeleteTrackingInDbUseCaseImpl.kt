package br.com.davidcastro.data.usecase.db.deletetrackingindbusecase

import br.com.davidcastro.data.repository.trackingdaorepository.TrackingDaoRepository

class DeleteTrackingInDbUseCaseImpl(
    private val trackingDaoRepository: TrackingDaoRepository
    ): DeleteTrackingInDbUseCase {

    override suspend fun deleteTracking(code: String) {
        trackingDaoRepository.delete(codigo = code)
    }
}