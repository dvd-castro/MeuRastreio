package br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase

import br.com.davidcastro.meurastreio.data.repository.TrackingRepository
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.coroutines.flow.Flow

class GetAllTrackingsInDbUseCaseImpl(
    private val repository: TrackingRepository
    ): GetAllTrackingsInDbUseCase {

    override suspend fun getAll(): Flow<List<TrackingDomain>> {
        return repository.getAll()
    }
}