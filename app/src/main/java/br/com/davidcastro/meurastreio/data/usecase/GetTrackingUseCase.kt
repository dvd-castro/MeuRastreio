package br.com.davidcastro.meurastreio.data.usecase

import br.com.davidcastro.meurastreio.data.model.TrackingModel

interface GetTrackingUseCase {
    suspend fun getTracking(code: String): TrackingModel?
}