package br.com.davidcastro.data.usecase.db

import br.com.davidcastro.data.repository.TrackingDaoRepository

class DeleteTrackingInDbUseCaseImpl(
    private val trackingDaoRepository: TrackingDaoRepository
    ): DeleteTrackingInDbUseCase {

    override suspend fun deleteTracking(code: String) {
        trackingDaoRepository.delete(codigo = code)
    }
}