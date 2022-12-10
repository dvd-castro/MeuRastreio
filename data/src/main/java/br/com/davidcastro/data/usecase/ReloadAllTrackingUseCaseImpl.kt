package br.com.davidcastro.data.usecase

import br.com.davidcastro.data.repository.TrackingDaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReloadAllTrackingUseCaseImpl(
    private val getTrackingUseCase: GetTrackingUseCase,
    private val trackingDaoRepository: TrackingDaoRepository
): ReloadAllTrackingUseCase {

    override suspend fun reload(): Boolean {
        var hasUpdate = false

        withContext(Dispatchers.IO) {
            trackingDaoRepository.getAll().getAllTrackingInProgress().forEach { trackingModel ->
                getTrackingUseCase.getTracking(trackingModel.code)?.let { result ->
                    if (result.events.count() > trackingModel.events.count()) {

                        result.apply {
                            name = trackingModel.name
                            hasUpdated = true
                        }

                        hasUpdate = true
                        trackingDaoRepository.update(result)
                    }
                }
            }
        }

        return hasUpdate
    }
}