package br.com.davidcastro.data.usecase.remote.gettrackingusecase

import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.trackingrepository.TrackingRepository

class GetTrackingUseCaseImpl(
    private val repository: TrackingRepository
    ): GetTrackingUseCase {

    override suspend fun getTracking(code: String): TrackingModel? {
        val result = repository.getTracking(code)

        return if (
            result.isSuccessful &&
            result.body() != null &&
            result.body()?.events?.isNotEmpty() == true
        ) {
            configStatusAndEvents(result.body())
        } else {
            null
        }
    }

    private fun configStatusAndEvents(tracking: TrackingModel?): TrackingModel? =
        tracking?.apply {
            events.find { it.status == "Objeto entregue ao destinatário" }?.let {
                hasCompleted = true
            }
            events.firstOrNull { it.status == "Objeto postado" }?.let {
                events.remove(it)
                events.add(it)
            }
        }
}