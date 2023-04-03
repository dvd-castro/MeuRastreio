package br.com.davidcastro.data.usecase.remote.gettrackingusecase

import br.com.davidcastro.data.model.TrackingModel

interface GetTrackingUseCase {
    suspend fun getTracking(code: String): TrackingModel?
}