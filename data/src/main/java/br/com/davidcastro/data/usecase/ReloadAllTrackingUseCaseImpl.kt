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
            trackingDaoRepository.getAll().forEach { trackingEntity ->
                val result =
                    getTrackingUseCase.getTracking(trackingEntity.code)?.toTrackingHome()
                if (result?.date != trackingEntity.date) {
                    result?.let {
                        it.hasUpdated = true
                        hasUpdate = true
                        trackingDaoRepository.insert(it.toTrackingEntity())
                    }
                }
            }
        }

        return hasUpdate
    }
}