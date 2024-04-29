package br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase

import br.com.davidcastro.meurastreio.data.repository.TrackingRepository
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

class InsertTrackingInDbUseCaseImpl(
    private val repository: TrackingRepository
    ): InsertTrackingInDbUseCase {

    override suspend fun insert(tracking: TrackingDomain) {
        repository.insert(tracking)
    }
}