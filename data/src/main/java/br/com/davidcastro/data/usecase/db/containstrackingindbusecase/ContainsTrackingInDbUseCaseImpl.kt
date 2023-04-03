package br.com.davidcastro.data.usecase.db.containstrackingindbusecase

import br.com.davidcastro.data.repository.trackingdaorepository.TrackingDaoRepository

class ContainsTrackingInDbUseCaseImpl(
    private val trackingDaoRepository: TrackingDaoRepository
    ): ContainsTrackingInDbUseCase {

    override suspend fun contains(codigo: String): Boolean =
        trackingDaoRepository.contains(codigo)

}