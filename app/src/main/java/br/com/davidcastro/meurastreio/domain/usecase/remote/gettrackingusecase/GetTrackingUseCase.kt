package br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.coroutines.flow.Flow

interface GetTrackingUseCase {
    suspend fun getTracking(code: String): Flow<TrackingDomain>
}