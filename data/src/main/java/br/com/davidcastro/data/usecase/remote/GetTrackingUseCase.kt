package br.com.davidcastro.data.usecase.remote

import br.com.davidcastro.data.model.TrackingModel

interface GetTrackingUseCase {
    suspend fun getTracking(code: String): TrackingModel?
}