package br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase

import br.com.davidcastro.meurastreio.data.repository.TrackingRepository
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrackingUseCaseImpl(
    private val repository: TrackingRepository
    ): GetTrackingUseCase {

    override suspend fun invoke(code: String): Flow<TrackingDomain> {
        val result = repository.getTracking(code)

        return result.map { tracking ->
            tracking.apply {
                events?.find { it.status == "Objeto entregue ao destinatário" }?.let {
                    hasCompleted = true
                }
            }
        }
    }
}