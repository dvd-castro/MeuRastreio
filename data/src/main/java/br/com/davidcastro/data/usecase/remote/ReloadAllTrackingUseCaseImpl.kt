package br.com.davidcastro.data.usecase.remote

import br.com.davidcastro.data.usecase.db.GetAllTrackingsInDbUseCase
import br.com.davidcastro.data.usecase.db.InsertTrackingInDbUseCase
import br.com.davidcastro.data.utils.getAllTrackingInProgress

class ReloadAllTrackingUseCaseImpl(
    private val getTrackingUseCase: GetTrackingUseCase,
    private val getAllTrackingsInDbUseCase: GetAllTrackingsInDbUseCase,
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase
): ReloadAllTrackingUseCase {

    override suspend fun reload(): Boolean {
        var hasUpdate = false

        getAllTrackingsInDbUseCase.getAll()
            .getAllTrackingInProgress()
            .forEach { trackingModel ->

                getTrackingUseCase.getTracking(trackingModel.code)?.let { result ->

                    if (result.events.count() > trackingModel.events.count()) {
                        result.apply {
                            name = trackingModel.name
                            hasUpdated = true
                        }

                        hasUpdate = true
                        insertTrackingInDbUseCase.insert(result)
                    }
                }
        }

        return hasUpdate
    }
}