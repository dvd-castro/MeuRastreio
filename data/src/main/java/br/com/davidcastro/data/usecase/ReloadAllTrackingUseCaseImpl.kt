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
            trackingDaoRepository.getAll().forEach { trackingModel ->
                val result = getTrackingUseCase.getTracking(trackingModel.code)
                if (result?.getLastEventDate() != trackingModel.getLastEventDate()) {
                    result?.let {
                        it.hasUpdated = true
                        hasUpdate = true
                        trackingDaoRepository.insert(it)
                    }
                }
            }
        }

        return hasUpdate
    }
}