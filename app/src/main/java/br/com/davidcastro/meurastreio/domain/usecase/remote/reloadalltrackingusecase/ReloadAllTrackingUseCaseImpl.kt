package br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase

import br.com.davidcastro.meurastreio.core.utils.getAllTrackingInProgress
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCase
import kotlinx.coroutines.flow.onEach

class ReloadAllTrackingUseCaseImpl(
    private val getTrackingUseCase: GetTrackingUseCase,
    private val getAllTrackingsInDbUseCase: GetAllTrackingsInDbUseCase,
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase
): ReloadAllTrackingUseCase {

    override suspend fun reload(): Boolean {
        var hasUpdate = false

        getAllTrackingsInDbUseCase.getAll().onEach { list ->
            list.getAllTrackingInProgress().forEach { trackingModel ->

                getTrackingUseCase.getTracking(trackingModel.code).onEach { tracking ->
                    tracking.let { result ->
                        if (result.events.count() > trackingModel.events.count()) {
                            trackingModel.apply {
                                hasUpdated = true
                                events = result.events
                            }

                            hasUpdate = true
                            insertTrackingInDbUseCase.insert(trackingModel)
                        }
                    }
                }
            }
        }

        return hasUpdate
    }
}