package br.com.davidcastro.data.usecase.db.inserttrackingindbusecase

import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.trackingdaorepository.TrackingDaoRepository

class InsertTrackingInDbUseCaseImpl(
    private val trackingDaoRepository: TrackingDaoRepository
    ): InsertTrackingInDbUseCase {

    override suspend fun insert(rastreio: TrackingModel) {
        trackingDaoRepository.insert(rastreio.toTrackingEntity())
    }
}