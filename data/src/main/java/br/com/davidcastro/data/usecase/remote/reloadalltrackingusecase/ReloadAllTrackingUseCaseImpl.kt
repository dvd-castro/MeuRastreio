package br.com.davidcastro.data.usecase.remote.reloadalltrackingusecase

import br.com.davidcastro.data.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.data.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.data.usecase.remote.gettrackingusecase.GetTrackingUseCase
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