package br.com.davidcastro.data.usecase.db.getalltrackingsindbusecase

import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.trackingdaorepository.TrackingDaoRepository
import br.com.davidcastro.data.utils.toTrackingModelList

class GetAllTrackingsInDbUseCaseImpl(
    private val trackingDaoRepository: TrackingDaoRepository
    ): GetAllTrackingsInDbUseCase {

    override suspend fun getAll(): List<TrackingModel> =
        trackingDaoRepository.getAll().toTrackingModelList()
}