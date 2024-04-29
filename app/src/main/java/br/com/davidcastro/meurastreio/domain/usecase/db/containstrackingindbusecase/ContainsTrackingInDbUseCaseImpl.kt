package br.com.davidcastro.meurastreio.domain.usecase.db.containstrackingindbusecase

import br.com.davidcastro.meurastreio.data.repository.TrackingRepository
import kotlinx.coroutines.flow.Flow

class ContainsTrackingInDbUseCaseImpl(
    private val repository: TrackingRepository
    ): ContainsTrackingInDbUseCase {

    override suspend fun contains(code: String): Flow<Boolean> =
        repository.contains(code)
}