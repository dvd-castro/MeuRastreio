package br.com.davidcastro.data.usecase.db

import br.com.davidcastro.data.repository.TrackingDaoRepository

class ContainsTrackingInDbUseCaseImpl(
    private val trackingDaoRepository: TrackingDaoRepository
    ): ContainsTrackingInDbUseCase {

    override suspend fun contains(codigo: String): Boolean =
        trackingDaoRepository.contains(codigo)

}