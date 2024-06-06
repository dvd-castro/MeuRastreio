package br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import kotlinx.coroutines.flow.Flow

interface GetAllTrackingsInDbUseCase {
    suspend operator fun invoke(): Flow<List<TrackingDomain>>
}