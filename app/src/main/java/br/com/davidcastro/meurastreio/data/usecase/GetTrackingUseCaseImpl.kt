package br.com.davidcastro.meurastreio.data.usecase

import br.com.davidcastro.meurastreio.data.model.TrackingModel
import br.com.davidcastro.meurastreio.data.repository.TrackingRepository

class GetTrackingUseCaseImpl(private val repository: TrackingRepository): GetTrackingUseCase {
    override suspend fun getTracking(code: String): TrackingModel? {
        val result = repository.getTracking(code)
        return if(result.isSuccessful && result.body() != null) {
            result.body()
        } else null
    }
}