package br.com.davidcastro.data.usecase

import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingRepository

class GetTrackingUseCaseImpl(private val repository: TrackingRepository): GetTrackingUseCase {
    override suspend fun getTracking(code: String): TrackingModel? {
        val result = repository.getTracking(code)
        return if(result.isSuccessful && result.body() != null) {
            setTrackingWithCompleted(result.body())
        } else null
    }

    private fun setTrackingWithCompleted(tracking: TrackingModel?): TrackingModel? =
        tracking?.apply {
            events.find { it.status == "Objeto entregue ao destinatário" }?.let {
                hasCompleted = true
            }
        }
}